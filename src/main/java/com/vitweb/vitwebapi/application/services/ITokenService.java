package com.vitweb.vitwebapi.application.services;

import com.vitweb.vitwebapi.adapter.web.v1.transfer.parameter.auth.RefreshPasswordRequest;
import com.vitweb.vitwebapi.adapter.web.v1.transfer.parameter.auth.VerifyForgotPasswordRequest;
import com.vitweb.vitwebapi.adapter.web.v1.transfer.response.RequestResponse;
import com.vitweb.vitwebapi.domain.entities.Token;
import com.vitweb.vitwebapi.domain.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ITokenService {

  List<Token> getAll();

  Token getTokenById(Long id);

  RequestResponse verify(String token);

  RequestResponse resendToken(String oldToken, String applicationUrl);

  void createTokenVerify(String token, User user);

  void createTokenVerify(String token, User user, int expirationTime);

  RequestResponse verifyForgotPassword(VerifyForgotPasswordRequest request);
}
