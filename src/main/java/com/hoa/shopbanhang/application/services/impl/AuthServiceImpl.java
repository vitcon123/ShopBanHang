package com.hoa.shopbanhang.application.services.impl;

import com.hoa.shopbanhang.adapter.web.v1.transfer.response.AuthenticationResponse;
import com.hoa.shopbanhang.adapter.web.v1.transfer.response.RequestResponse;
import com.hoa.shopbanhang.application.constants.*;
import com.hoa.shopbanhang.application.events.SignUpEvent;
import com.hoa.shopbanhang.application.inputs.auth.AuthenticationRequest;
import com.hoa.shopbanhang.application.inputs.auth.UpdatePasswordInput;
import com.hoa.shopbanhang.application.inputs.user.CreateUserInput;
import com.hoa.shopbanhang.application.repositories.IRoleRepository;
import com.hoa.shopbanhang.application.repositories.ITokenRepository;
import com.hoa.shopbanhang.application.repositories.IUserRepository;
import com.hoa.shopbanhang.application.services.IAuthService;
import com.hoa.shopbanhang.application.services.ICartService;
import com.hoa.shopbanhang.application.utils.JwtUtil;
import com.hoa.shopbanhang.application.utils.SendMailUtil;
import com.hoa.shopbanhang.configs.exceptions.NotFoundException;
import com.hoa.shopbanhang.configs.exceptions.VsException;
import com.hoa.shopbanhang.domain.entities.Role;
import com.hoa.shopbanhang.domain.entities.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        jwtUtil.generateToken(oldUser.get(), CommonConstant.TRUE), oldUser.get());
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
        Optional<User> user = userRepository.findByEmail(email);
        UserServiceImpl.checkUserExists(user);

        return new AuthenticationResponse(accessToken, CommonConstant.BEARER_TOKEN, refreshToken, user.get());
      }
    } catch (Exception ex) {
      response.setHeader("error", ex.getMessage());
    }
    return null;
  }


  @Override
  public RequestResponse resetPassword(String email) {
    Optional<User> user = userRepository.findByEmail(email);
    UserServiceImpl.checkUserExists(user);

    UUID uuid = UUID.randomUUID();
    String uuidString = uuid.toString();

    String newPassword = uuidString.substring(uuidString.length() - 8);

    user.get().setPassword(passwordEncoder.encode(newPassword));
    userRepository.save(user.get());

    String contentAccount =
        "We have received your request to reset your password"
        + ".\nYour new password is: " + newPassword
        + "\n\nThanks for using our service.";

    try {
      SendMailUtil.sendMailSimple(user.get().getEmail(), contentAccount, EmailConstant.SUBJECT_RESET_PASSWORD);
    } catch (Exception e) {
      throw new NotFoundException(EmailConstant.SEND_FAILED);
    }
    return new RequestResponse(CommonConstant.TRUE, MessageConstant.CHECK_YOUR_EMAIL);
  }

  @Override
  public RequestResponse updatePassword(UpdatePasswordInput updatePasswordInput) {
    Optional<User> user = userRepository.findById(updatePasswordInput.getId());
    UserServiceImpl.checkUserExists(user);

    if(passwordEncoder.matches(updatePasswordInput.getOldPassword(), user.get().getPassword())) {
      user.get().setPassword(passwordEncoder.encode(updatePasswordInput.getNewPassword()));
      userRepository.save(user.get());
    }
    else {
      throw new VsException(MessageConstant.INCORRECT_PASSWORD);
    }

    return new RequestResponse(CommonConstant.TRUE, MessageConstant.UPDATE_PASSWORD_SUCCESSFUL);

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
