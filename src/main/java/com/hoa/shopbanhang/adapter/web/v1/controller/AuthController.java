package com.hoa.shopbanhang.adapter.web.v1.controller;

import com.hoa.shopbanhang.adapter.web.base.RestApiV1;
import com.hoa.shopbanhang.adapter.web.base.VsResponseUtil;
import com.hoa.shopbanhang.adapter.web.v1.transfer.parameter.auth.AuthenticationRequest;
import com.hoa.shopbanhang.adapter.web.v1.transfer.parameter.auth.ChangePasswordRequest;
import com.hoa.shopbanhang.adapter.web.v1.transfer.parameter.auth.RefreshPasswordRequest;
import com.hoa.shopbanhang.adapter.web.v1.transfer.parameter.auth.UpdatePasswordInput;
import com.hoa.shopbanhang.application.constants.UrlConstant;
import com.hoa.shopbanhang.application.inputs.user.CreateUserInput;
import com.hoa.shopbanhang.application.services.IAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) {
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
    public ResponseEntity<?> resetPassword(@RequestParam("email") String email) {
        return VsResponseUtil.ok(authService.createPasswordResetTokenForAccount(email));
    }

    @GetMapping(UrlConstant.Auth.VERIFY_RESET_PASSWORD)
    public ResponseEntity<?> verificationTokenResetPassword(@RequestParam(name = "token") String token) {
        return VsResponseUtil.ok(authService.verificationTokenResetPassword(token));
    }

    @PostMapping(UrlConstant.Auth.UPDATE_PASSWORD)
    public ResponseEntity<?> updatePassword(@Valid @RequestBody UpdatePasswordInput input) {
        return VsResponseUtil.ok(authService.updatePassword(input));
    }

    private String applicationUrl(HttpServletRequest request) {
        return "https://" +
                request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath();
    }
}
