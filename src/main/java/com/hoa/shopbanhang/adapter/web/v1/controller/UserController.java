package com.hoa.shopbanhang.adapter.web.v1.controller;

import com.hoa.shopbanhang.adapter.web.base.RestApiV1;
import com.hoa.shopbanhang.adapter.web.base.VsResponseUtil;
import com.hoa.shopbanhang.application.constants.UrlConstant;
import com.hoa.shopbanhang.application.inputs.user.ChangeAvatarInput;
import com.hoa.shopbanhang.application.inputs.user.UpdateUserInput;
import com.hoa.shopbanhang.application.services.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestApiV1
public class UserController {

  private final IUserService userService;

  public UserController(IUserService userService) {
    this.userService = userService;
  }

  @GetMapping(UrlConstant.User.LIST)
  public ResponseEntity<?> getAll() {
    return VsResponseUtil.ok(userService.getAll());
  }

  @GetMapping(UrlConstant.User.GET)
  public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
    return VsResponseUtil.ok(userService.getUserById(id));
  }

  @PatchMapping(UrlConstant.User.UPDATE)
  public ResponseEntity<?> updateUser(@RequestBody UpdateUserInput updateUserInput) {
    return VsResponseUtil.ok(userService.updateUser(updateUserInput));
  }

  @PostMapping(UrlConstant.User.CHANGE_AVATAR)
  public ResponseEntity<?> changeAvatarUser(@ModelAttribute ChangeAvatarInput changeAvatarInput) {
    return VsResponseUtil.ok(userService.changeAvatar(changeAvatarInput));
  }

  @DeleteMapping(UrlConstant.User.DELETE)
  public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
    return VsResponseUtil.ok(userService.deleteById(id));
  }
  
}
