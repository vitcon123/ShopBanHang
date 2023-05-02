package com.hoa.shopbanhang.adapter.web.v1.controller;

import com.hoa.shopbanhang.adapter.web.base.RestApiV1;
import com.hoa.shopbanhang.adapter.web.base.VsResponseUtil;
import com.hoa.shopbanhang.application.inputs.auth.AuthenticationRequest;
import com.hoa.shopbanhang.application.constants.UrlConstant;
import com.hoa.shopbanhang.application.inputs.auth.UpdatePasswordInput;
import com.hoa.shopbanhang.application.inputs.user.CreateUserInput;
import com.hoa.shopbanhang.application.services.IAuthService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Log In")
    @PostMapping(UrlConstant.Auth.LOGIN)
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) {
        return VsResponseUtil.ok(authService.login(authenticationRequest));
    }

    @Operation(summary = "Sign Up")
    @PostMapping (UrlConstant.Auth.SIGNUP)
    public ResponseEntity<?> signUp(@Valid @RequestBody CreateUserInput createUserInput,
                                    HttpServletRequest request) {
        return VsResponseUtil.ok(authService.signUp(createUserInput, request));
    }

    @Operation(summary = "Refresh Token")
    @PostMapping(UrlConstant.Auth.REFRESH_TOKEN)
    public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.ok().body(authService.refreshToken(request, response));
    }

    @Operation(summary = "Reset Password")
    @PostMapping(UrlConstant.Auth.RESET_PASSWORD)
    public ResponseEntity<?> resetPassword(@RequestParam("email") String email) {
        return VsResponseUtil.ok(authService.resetPassword(email));
    }

    @Operation(summary = "Update Password")
    @PostMapping(UrlConstant.Auth.UPDATE_PASSWORD)
    public ResponseEntity<?> updatePassword(@Valid @RequestBody UpdatePasswordInput input) {
        return VsResponseUtil.ok(authService.updatePassword(input));
    }

}
