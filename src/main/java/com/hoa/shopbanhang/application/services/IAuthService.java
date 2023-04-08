package com.hoa.shopbanhang.application.services;

import com.hoa.shopbanhang.adapter.web.v1.transfer.parameter.auth.AuthenticationRequest;
import com.hoa.shopbanhang.adapter.web.v1.transfer.parameter.auth.ChangePasswordRequest;
import com.hoa.shopbanhang.adapter.web.v1.transfer.parameter.auth.RefreshPasswordRequest;
import com.hoa.shopbanhang.adapter.web.v1.transfer.parameter.auth.UpdatePasswordInput;
import com.hoa.shopbanhang.adapter.web.v1.transfer.response.AuthenticationResponse;
import com.hoa.shopbanhang.adapter.web.v1.transfer.response.RequestResponse;
import com.hoa.shopbanhang.application.inputs.user.CreateUserInput;
import com.hoa.shopbanhang.domain.entities.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IAuthService {

  AuthenticationResponse login(AuthenticationRequest authenticationRequest);

  User signUp(CreateUserInput createUserInput, HttpServletRequest request);

  AuthenticationResponse refreshToken(HttpServletRequest request, HttpServletResponse response);

  RequestResponse createPasswordResetTokenForAccount(String email);

  void saveVerificationTokenResetPassword(User account, String token);

  RequestResponse verificationTokenResetPassword(String token);

  RequestResponse updatePassword(UpdatePasswordInput input);

}
