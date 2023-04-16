package com.hoa.shopbanhang.adapter.web.v1.controller;

import com.hoa.shopbanhang.adapter.web.base.RestApiV1;
import com.hoa.shopbanhang.adapter.web.base.VsResponseUtil;
import com.hoa.shopbanhang.application.constants.UrlConstant;
import com.hoa.shopbanhang.application.inputs.user.ChangeAvatarInput;
import com.hoa.shopbanhang.application.inputs.user.UpdateUserInput;
import com.hoa.shopbanhang.application.services.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestApiV1
public class UserController {

  private final IUserService userService;

  public UserController(IUserService userService) {
    this.userService = userService;
  }

  @Operation(summary = "Get All User - ADMIN")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping(UrlConstant.User.LIST)
  public ResponseEntity<?> getAll() {
    return VsResponseUtil.ok(userService.getAll());
  }

  @Operation(summary = "Get User By Id")
  @GetMapping(UrlConstant.User.GET)
  public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
    return VsResponseUtil.ok(userService.getUserById(id));
  }

  @Operation(summary = "Update User - ADMIN, USER")
  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
  @PatchMapping(UrlConstant.User.UPDATE)
  public ResponseEntity<?> updateUser(@RequestBody UpdateUserInput updateUserInput) {
    return VsResponseUtil.ok(userService.updateUser(updateUserInput));
  }

  @Operation(summary = "Change Avatar User - ADMIN, USER")
  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
  @PostMapping(UrlConstant.User.CHANGE_AVATAR)
  public ResponseEntity<?> changeAvatarUser(@ModelAttribute ChangeAvatarInput changeAvatarInput) {
    return VsResponseUtil.ok(userService.changeAvatar(changeAvatarInput));
  }

  @Operation(summary = "Delete User - ADMIN, USER")
  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
  @DeleteMapping(UrlConstant.User.DELETE)
  public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
    return VsResponseUtil.ok(userService.deleteById(id));
  }
  
}
