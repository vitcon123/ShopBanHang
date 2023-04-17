package com.hoa.shopbanhang.application.services;

import com.hoa.shopbanhang.adapter.web.v1.transfer.response.RequestResponse;
import com.hoa.shopbanhang.domain.entities.Token;
import com.hoa.shopbanhang.domain.entities.User;

import java.util.List;

public interface ITokenService {

  List<Token> getAll();

  RequestResponse verify(String token);

  RequestResponse resendToken(String oldToken, String applicationUrl);

  void createTokenVerify(String token, User user);

//  RequestResponse verifyForgotPassword(VerifyForgotPasswordRequest request);
}
