package com.vitweb.vitwebapi.adapter.web.v1.controller;

import com.vitweb.vitwebapi.adapter.web.base.RestApiV1;
import com.vitweb.vitwebapi.adapter.web.base.VsResponseUtil;
import com.vitweb.vitwebapi.adapter.web.v1.transfer.parameter.auth.VerifyForgotPasswordRequest;
import com.vitweb.vitwebapi.application.constants.UrlConstant;
import com.vitweb.vitwebapi.application.services.ITokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestApiV1
public class TokenController {

  private final ITokenService tokenService;

  public TokenController(ITokenService tokenService) {
    this.tokenService = tokenService;
  }

  @GetMapping(UrlConstant.Token.VERIFY)
  public ResponseEntity<?> verifyToken(@PathVariable("token") String token) {
    return VsResponseUtil.ok(tokenService.verify(token));
  }

  @PostMapping(UrlConstant.Token.VERIFY_FORGOT_PASSWORD)
  public ResponseEntity<?> verifyForgotPassword(@Valid @ModelAttribute VerifyForgotPasswordRequest request) {
    return VsResponseUtil.ok(tokenService.verifyForgotPassword(request));
  }

  @PostMapping(UrlConstant.Token.RESEND)
  public ResponseEntity<?> resendToken(@PathVariable("token") String token, HttpServletRequest request) {
    return VsResponseUtil.ok(tokenService.resendToken(token, applicationUrl(request)));
  }

  private String applicationUrl(HttpServletRequest request) {
    return "https://" +
        request.getServerName() +
        ":" +
        request.getServerPort() +
        request.getContextPath();
  }
}
