package com.vitweb.vitwebapi.adapter.web.v1.controller;

import com.vitweb.vitwebapi.adapter.web.base.RestApiV1;
import com.vitweb.vitwebapi.adapter.web.base.VsResponseUtil;
import com.vitweb.vitwebapi.adapter.web.v1.transfer.parameter.auth.AuthenticationRequest;
import com.vitweb.vitwebapi.adapter.web.v1.transfer.parameter.auth.ChangePasswordRequest;
import com.vitweb.vitwebapi.adapter.web.v1.transfer.parameter.auth.RefreshPasswordRequest;
import com.vitweb.vitwebapi.application.constants.UrlConstant;
import com.vitweb.vitwebapi.application.events.SignUpEvent;
import com.vitweb.vitwebapi.application.inputs.user.CreateUserInput;
import com.vitweb.vitwebapi.application.services.IAuthService;
import com.vitweb.vitwebapi.domain.entities.User;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestApiV1
public class AuthController {

    private final IAuthService authService;

    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @PostMapping(UrlConstant.Auth.LOGIN)
    public ResponseEntity<?> login(@Valid @ModelAttribute AuthenticationRequest authenticationRequest) {
        return VsResponseUtil.ok(authService.login(authenticationRequest));
    }

    @PostMapping (UrlConstant.Auth.SIGNUP)
    public ResponseEntity<?> signUp(@Valid @RequestBody CreateUserInput createUserInput,
                                    HttpServletRequest request) {
        return VsResponseUtil.ok(authService.signUp(createUserInput, request));
    }

    @PostMapping(UrlConstant.Auth.REFRESH_TOKEN)
    public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.ok().body(authService.refreshToken(request, response));
    }

    @PostMapping(UrlConstant.Auth.FORGOT_PASSWORD)
    public ResponseEntity<?> forgotPassword(@Valid @PathVariable("email") String email, HttpServletRequest request) {
        return VsResponseUtil.ok(authService.forgotPassword(email, applicationUrl(request)));
    }

    @PostMapping(UrlConstant.Auth.REFRESH_PASSWORD)
    public ResponseEntity<?> refreshPassword(@Valid @ModelAttribute RefreshPasswordRequest request) {
        return VsResponseUtil.ok(authService.refreshPassword(request));
    }

    @PostMapping(UrlConstant.Auth.CHANGE_PASSWORD)
    public ResponseEntity<?> changePassword(@Valid @ModelAttribute ChangePasswordRequest changePasswordRequest) {
        return VsResponseUtil.ok(authService.changePassword(changePasswordRequest));
    }

    private String applicationUrl(HttpServletRequest request) {
        return "https://" +
                request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath();
    }
}
