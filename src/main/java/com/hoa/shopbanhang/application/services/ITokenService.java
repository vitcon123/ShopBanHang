package com.hoa.shopbanhang.application.services;

import com.hoa.shopbanhang.adapter.web.v1.transfer.parameter.auth.VerifyForgotPasswordRequest;
import com.hoa.shopbanhang.adapter.web.v1.transfer.response.RequestResponse;
import com.hoa.shopbanhang.domain.entities.Token;
import com.hoa.shopbanhang.domain.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ITokenService {

  List<Token> getAll();

  Token getTokenById(Long id);

  RequestResponse verify(String token);

  RequestResponse resendToken(String oldToken, String applicationUrl);

  void createTokenVerify(String token, User user);

  void createTokenVerify(String token, User user, int expirationTime);

  RequestResponse verifyForgotPassword(VerifyForgotPasswordRequest request);
}
