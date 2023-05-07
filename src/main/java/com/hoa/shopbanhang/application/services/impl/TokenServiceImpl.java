package com.hoa.shopbanhang.application.services.impl;

import com.hoa.shopbanhang.adapter.web.v1.transfer.response.RequestResponse;
import com.hoa.shopbanhang.application.constants.CommonConstant;
import com.hoa.shopbanhang.application.constants.DevMessageConstant;
import com.hoa.shopbanhang.application.constants.UserMessageConstant;
import com.hoa.shopbanhang.application.repositories.ITokenRepository;
import com.hoa.shopbanhang.application.repositories.IUserRepository;
import com.hoa.shopbanhang.application.services.ITokenService;
import com.hoa.shopbanhang.application.utils.SendMailUtil;
import com.hoa.shopbanhang.configs.exceptions.VsException;
import com.hoa.shopbanhang.domain.entities.Token;
import com.hoa.shopbanhang.domain.entities.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TokenServiceImpl implements ITokenService {
  private final ITokenRepository tokenRepository;
  private final IUserRepository userRepository;


  public TokenServiceImpl(ITokenRepository tokenRepository, IUserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
    this.tokenRepository = tokenRepository;
    this.userRepository = userRepository;
  }

  @Override
  public List<Token> getAll() {
    return tokenRepository.findAll();
  }

  @Override
  public RequestResponse verify(String token) {
    try {
      Token oldToken = validateToken(token);
      User user = oldToken.getUser();

      // Update status user and verification token
      user.setStatus(true);
      userRepository.save(user);
    } catch (Exception ex) {
      throw new VsException(ex.getMessage());
    }

    return new RequestResponse(CommonConstant.TRUE, "Verify Successfully!");
  }

  @Override
  public RequestResponse resendToken(String token, String applicationUrl) {
    Optional<Token> oldToken = tokenRepository.findByToken(token);
    checkTokenExists(oldToken);

    String newToken = UUID.randomUUID().toString();
    oldToken.get().setToken(newToken);
    tokenRepository.save(oldToken.get());

    User user = oldToken.get().getUser();

    String url = applicationUrl
        + "/api/v1/tokens/verify/"
        + newToken;
    SendMailUtil.sendMailSimple(user.getEmail(), url, "Verify sign up Shop Cua Hoa");
    return new RequestResponse(CommonConstant.TRUE, "");
  }

  @Override
  public void createTokenVerify(String token, User user) {
    Token newToken = new Token(token, user);
    tokenRepository.save(newToken);
  }

  private Token validateToken(String token){
    Optional<Token> oldToken = tokenRepository.findByToken(token);
    checkTokenExists(oldToken);

    Calendar calendar = Calendar.getInstance();
    if((oldToken.get().getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0) {
      tokenRepository.delete(oldToken.get());
      throw new VsException("Token expired !");
    }
    return oldToken.get();
  }

  private void checkTokenExists(Optional<Token> Token) {
    if(Token.isEmpty()) {
      throw new VsException(UserMessageConstant.ERR_EXCEPTION_GENERAL,
          DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID);
    }
  }
}
