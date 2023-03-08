package com.hoa.shopbanhang.application.services.impl;

import com.hoa.shopbanhang.adapter.web.v1.transfer.parameter.auth.AuthenticationRequest;
import com.hoa.shopbanhang.adapter.web.v1.transfer.parameter.auth.ChangePasswordRequest;
import com.hoa.shopbanhang.adapter.web.v1.transfer.parameter.auth.RefreshPasswordRequest;
import com.hoa.shopbanhang.adapter.web.v1.transfer.response.AuthenticationResponse;
import com.hoa.shopbanhang.adapter.web.v1.transfer.response.RequestResponse;
import com.hoa.shopbanhang.application.constants.AuthenticationProvider;
import com.hoa.shopbanhang.application.constants.CommonConstant;
import com.hoa.shopbanhang.application.constants.RoleConstant;
import com.hoa.shopbanhang.application.events.SignUpEvent;
import com.hoa.shopbanhang.application.inputs.user.CreateUserInput;
import com.hoa.shopbanhang.application.repositories.IRoleRepository;
import com.hoa.shopbanhang.application.repositories.IUserRepository;
import com.hoa.shopbanhang.application.services.IAuthService;
import com.hoa.shopbanhang.application.services.ICartService;
import com.hoa.shopbanhang.application.services.ITokenService;
import com.hoa.shopbanhang.application.utils.JwtUtil;
import com.hoa.shopbanhang.application.utils.SendMailUtil;
import com.hoa.shopbanhang.configs.exceptions.VsException;
import com.hoa.shopbanhang.domain.entities.Role;
import com.hoa.shopbanhang.domain.entities.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.RandomStringUtils;
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
import java.util.Date;
import java.util.Optional;

@Service
public class AuthServiceImpl implements IAuthService {

  private final IUserRepository userRepository;
  private final JwtUtil jwtUtil;
  private final ModelMapper modelMapper;
  private final ITokenService tokenService;
  private final ICartService cartService;
  private final PasswordEncoder passwordEncoder;
  private final IRoleRepository roleRepository;
  private final AuthenticationManager authenticationManager;
  private final ApplicationEventPublisher publisher;
  @Value("${jwt.secret_key}")
  private String SECRET_KEY;
  @Value("${jwt.time_token_expiration}")
  private Integer TIME_TOKEN_EXPIRATION;


  public AuthServiceImpl(IUserRepository userRepository, JwtUtil jwtUtil, ModelMapper modelMapper,
                         ITokenService tokenService, ICartService cartService, PasswordEncoder passwordEncoder,
                         IRoleRepository roleRepository, AuthenticationManager authenticationManager,
                         ApplicationEventPublisher publisher) {
    this.userRepository = userRepository;
    this.jwtUtil = jwtUtil;
    this.modelMapper = modelMapper;
    this.tokenService = tokenService;
    this.cartService = cartService;
    this.passwordEncoder = passwordEncoder;
    this.roleRepository = roleRepository;
    this.authenticationManager = authenticationManager;
    this.publisher = publisher;
  }

  @Override
  public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
    Optional<User> oldUser = userRepository.findByEmail(authenticationRequest.getEmail());
    checkUserExists(oldUser);

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
  public RequestResponse forgotPassword(String email, String applicationUrl) {
    Optional<User> user = userRepository.findByEmail(email);
    checkUserExists(user);

    String sixNum = RandomStringUtils.randomNumeric(6);
    tokenService.createTokenVerify(sixNum, user.get(), 60);
    SendMailUtil.sendMailSimple(user.get().getEmail(), "Mã khôi phục mật khẩu: " + sixNum, "Yêu cầu khôi phục mật " +
        "khẩu Vit Web");
    return new RequestResponse(CommonConstant.TRUE, "");
  }

  @Override
  public RequestResponse refreshPassword(RefreshPasswordRequest request) {
    Optional<User> user = userRepository.findByEmail(request.getEmail());
    checkUserExists(user);

    user.get().setPassword(passwordEncoder.encode(request.getNewPassword()));
    userRepository.save(user.get());
    return new RequestResponse(CommonConstant.TRUE, "Refresh password successfully !");
  }

  @Override
  public RequestResponse changePassword(ChangePasswordRequest request) {
    Optional<User> user = userRepository.findByEmail(request.getEmail());
    checkUserExists(user);

    String oldPasswordInput = passwordEncoder.encode(request.getOldPassword());
    if (!user.get().getPassword().equals(oldPasswordInput)) {
      throw new VsException("The current password entered is incorrect");
    }

    user.get().setPassword(passwordEncoder.encode(request.getNewPassword()));
    userRepository.save(user.get());
    return new RequestResponse(CommonConstant.TRUE, "Change password successfully");
  }

  private String getTokenFromRequest(HttpServletRequest request) {
    String authorizationHeader = request.getHeader("Authorization");

    if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
      return authorizationHeader.substring(7);
    }
    return null;
  }

  private void checkUserExists(Optional<User> user) {
    if (user.isEmpty()) {
      throw new VsException("User not found");
    }
  }

  private String applicationUrl(HttpServletRequest request) {
    return "http://" +
        request.getServerName() +
        ":" +
        request.getServerPort() +
        request.getContextPath();
  }
}
