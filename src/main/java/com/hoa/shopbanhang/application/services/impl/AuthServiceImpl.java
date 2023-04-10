package com.hoa.shopbanhang.application.services.impl;

import com.hoa.shopbanhang.adapter.web.v1.transfer.parameter.auth.AuthenticationRequest;
import com.hoa.shopbanhang.adapter.web.v1.transfer.parameter.auth.UpdatePasswordInput;
import com.hoa.shopbanhang.adapter.web.v1.transfer.response.AuthenticationResponse;
import com.hoa.shopbanhang.adapter.web.v1.transfer.response.RequestResponse;
import com.hoa.shopbanhang.application.constants.*;
import com.hoa.shopbanhang.application.events.SignUpEvent;
import com.hoa.shopbanhang.application.inputs.user.CreateUserInput;
import com.hoa.shopbanhang.application.repositories.IRoleRepository;
import com.hoa.shopbanhang.application.repositories.ITokenRepository;
import com.hoa.shopbanhang.application.repositories.IUserRepository;
import com.hoa.shopbanhang.application.services.IAuthService;
import com.hoa.shopbanhang.application.services.ICartService;
import com.hoa.shopbanhang.application.services.ITokenService;
import com.hoa.shopbanhang.application.utils.JwtUtil;
import com.hoa.shopbanhang.application.utils.SendMailUtil;
import com.hoa.shopbanhang.application.utils.UrlUtil;
import com.hoa.shopbanhang.configs.exceptions.NotFoundException;
import com.hoa.shopbanhang.configs.exceptions.VsException;
import com.hoa.shopbanhang.domain.entities.Role;
import com.hoa.shopbanhang.domain.entities.Token;
import com.hoa.shopbanhang.domain.entities.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthServiceImpl implements IAuthService {

  private final IUserRepository userRepository;
  private final JwtUtil jwtUtil;
  private final ModelMapper modelMapper;
  private final ITokenRepository tokenRepository;
  private final ICartService cartService;
  private final PasswordEncoder passwordEncoder;
  private final IRoleRepository roleRepository;
  private final AuthenticationManager authenticationManager;
  private final ApplicationEventPublisher publisher;
  private final HttpServletRequest request;

  @Value("${jwt.secret_key}")
  private String SECRET_KEY;
  @Value("${jwt.time_token_expiration}")
  private Integer TIME_TOKEN_EXPIRATION;


  public AuthServiceImpl(IUserRepository userRepository, JwtUtil jwtUtil, ModelMapper modelMapper,
                         ITokenRepository tokenRepository, ICartService cartService, PasswordEncoder passwordEncoder,
                         IRoleRepository roleRepository, AuthenticationManager authenticationManager,
                         ApplicationEventPublisher publisher, HttpServletRequest request) {
    this.userRepository = userRepository;
    this.jwtUtil = jwtUtil;
    this.modelMapper = modelMapper;
    this.tokenRepository = tokenRepository;
    this.cartService = cartService;
    this.passwordEncoder = passwordEncoder;
    this.roleRepository = roleRepository;
    this.authenticationManager = authenticationManager;
    this.publisher = publisher;
    this.request = request;
  }

  @Override
  public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
    Optional<User> oldUser = userRepository.findByEmail(authenticationRequest.getEmail());
    UserServiceImpl.checkUserExists(oldUser);

    // check user authentication
    if (!oldUser.get().getStatus()) {
      throw new VsException("User unconfirmed account!");
    }

    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
          authenticationRequest.getEmail(), authenticationRequest.getPassword()
      ));
    } catch (BadCredentialsException e) {
      throw new VsException("Incorrect username or password");
    }

    return new AuthenticationResponse(jwtUtil.generateToken(oldUser.get(), CommonConstant.FALSE),
        CommonConstant.BEARER_TOKEN,
        jwtUtil.generateToken(oldUser.get(), CommonConstant.TRUE));
  }

  @Override
  public User signUp(CreateUserInput createUserInput, HttpServletRequest request) {
    Optional<User> oldUser = userRepository.findByEmail(createUserInput.getEmail());
    if (oldUser.isPresent()) {
      throw new VsException("Email has already exists");
    }

    User user = modelMapper.map(createUserInput, User.class);
    user.setPassword(passwordEncoder.encode(createUserInput.getPassword()));

    Role role = roleRepository.findByName(RoleConstant.ROLE_USER);
    user.setRole(role);
    user.setAuthProvider(AuthenticationProvider.LOCAL);

    Long id = userRepository.save(user).getId();
    cartService.createCart(id);

    publisher.publishEvent(new SignUpEvent(
        user,
        applicationUrl(request)
    ));

    return user;
  }

  @Override
  public AuthenticationResponse refreshToken(HttpServletRequest request, HttpServletResponse response) {
    try {
      String refreshToken = getTokenFromRequest(request);
      if (refreshToken != null && jwtUtil.validateJwtToken(refreshToken)) {
        String email = jwtUtil.getEmailFromJwtToken(refreshToken);
        String accessToken = Jwts.builder()
            .setSubject(email)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + TIME_TOKEN_EXPIRATION))
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
            .compact();
        return new AuthenticationResponse(accessToken, CommonConstant.BEARER_TOKEN, refreshToken);
      }
    } catch (Exception ex) {
      response.setHeader("error", ex.getMessage());
    }
    return null;
  }


  @Override
  public RequestResponse createPasswordResetTokenForAccount(String email) {
    Optional<User> user = userRepository.findByEmail(email);
    UserServiceImpl.checkUserExists(user);

    String token = UUID.randomUUID().toString();

    saveVerificationTokenResetPassword(user.get(), token);

    // Send Mail to Account
    String url =
        UrlUtil.applicationUrl(request)
            + "/api/v1"
            + UrlConstant.Auth.VERIFY_RESET_PASSWORD
            + "?token=" + token;

    String contentAccount =
        "Chúng tôi đã nhận được yêu cầu đổi mật khẩu của bạn"
            + ".\n\nĐể xác nhận thay đổi mật khẩu, vui lòng nhấn vào link sau: " + url
            + ".\n\nCảm ơn vì đã xử dụng dịch vụ của chúng tôi.";

    try {
      SendMailUtil.sendMailSimple(user.get().getEmail(), contentAccount, EmailConstant.SUBJECT_RESET_PASSWORD);
    } catch (Exception e) {
      throw new NotFoundException(EmailConstant.SEND_FAILED);
    }

    return new RequestResponse(CommonConstant.TRUE, EmailConstant.SENT_SUCCESSFULLY);
  }

  @Override
  public void saveVerificationTokenResetPassword(User user, String token) {
    Token passwordResetToken
        = new Token(token, user);
    tokenRepository.save(passwordResetToken);
  }

  @Override
  public RequestResponse verificationTokenResetPassword(String token) {
    Optional<Token> passwordResetToken
        = tokenRepository.findByToken(token);
    if (passwordResetToken == null) {
      throw new NotFoundException(MessageConstant.INVALID_TOKEN);
    }
    Calendar cal = Calendar.getInstance();
    if ((passwordResetToken.get().getExpirationTime().getTime()
        - cal.getTime().getTime()) <= 0) {
      tokenRepository.delete(passwordResetToken.get());
      throw new NotFoundException(MessageConstant.EXPIRED_TOKEN);
    }
    return new RequestResponse(CommonConstant.TRUE, UserMessageConstant.CONFIRMED_TOKEN_RESET_PASSWORD);
  }

  @Override
  public RequestResponse updatePassword(UpdatePasswordInput input) {
    Optional<User> user = userRepository.findByEmail(input.getEmail());
    UserServiceImpl.checkUserExists(user);
    user.get().setPassword(passwordEncoder.encode(input.getNewPassword()));
    tokenRepository.delete(tokenRepository.findByToken(input.getToken()).get());
    userRepository.save(user.get());
    return new RequestResponse(CommonConstant.TRUE, UserMessageConstant.RESET_PASSWORD_SUCCESS);
  }


  private String getTokenFromRequest(HttpServletRequest request) {
    String authorizationHeader = request.getHeader("Authorization");

    if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
      return authorizationHeader.substring(7);
    }
    return null;
  }

  private String applicationUrl(HttpServletRequest request) {
    return "http://" +
        request.getServerName() +
        ":" +
        request.getServerPort() +
        request.getContextPath();
  }
}
