package com.vitweb.vitwebapi.application.services;

import com.vitweb.vitwebapi.adapter.web.v1.transfer.parameter.auth.AuthenticationRequest;
import com.vitweb.vitwebapi.adapter.web.v1.transfer.parameter.auth.ChangePasswordRequest;
import com.vitweb.vitwebapi.adapter.web.v1.transfer.parameter.auth.RefreshPasswordRequest;
import com.vitweb.vitwebapi.adapter.web.v1.transfer.response.AuthenticationResponse;
import com.vitweb.vitwebapi.adapter.web.v1.transfer.response.RequestResponse;
import com.vitweb.vitwebapi.application.inputs.user.CreateUserInput;
import com.vitweb.vitwebapi.domain.entities.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public interface IAuthService {

  AuthenticationResponse login(AuthenticationRequest authenticationRequest);

  User signUp(CreateUserInput createUserInput, HttpServletRequest request);

  AuthenticationResponse refreshToken(HttpServletRequest request, HttpServletResponse response);

  RequestResponse refreshPassword(RefreshPasswordRequest request);

  RequestResponse forgotPassword(String email, String applicationUrl);

  RequestResponse changePassword(ChangePasswordRequest request);

}
