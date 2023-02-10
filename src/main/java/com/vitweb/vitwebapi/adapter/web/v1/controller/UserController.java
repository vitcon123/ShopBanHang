package com.vitweb.vitwebapi.adapter.web.v1.controller;

import com.vitweb.vitwebapi.adapter.web.base.RestApiV1;
import com.vitweb.vitwebapi.adapter.web.base.VsResponseUtil;
import com.vitweb.vitwebapi.application.constants.UrlConstant;
import com.vitweb.vitwebapi.application.inputs.user.ChangeAvatarInput;
import com.vitweb.vitwebapi.application.inputs.user.UpdateUserInput;
import com.vitweb.vitwebapi.application.services.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

  @GetMapping(UrlConstant.User.GET_FOLLOWING)
  public ResponseEntity<?> getFollowing(@PathVariable("id") Long id) {
    return VsResponseUtil.ok(userService.getFollowing(id));
  }

  @GetMapping(UrlConstant.User.GET_FOLLOWERS)
  public ResponseEntity<?> getFollowers(@PathVariable("id") Long id) {
    return VsResponseUtil.ok(userService.getFollowers(id));
  }

  @GetMapping(UrlConstant.User.GET)
  public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
    return VsResponseUtil.ok(userService.getUserById(id));
  }

  @GetMapping(UrlConstant.User.GET_ID_NAME)
  public ResponseEntity<?> getUserByIdName(@PathVariable("idName") String idName) {
    return VsResponseUtil.ok(userService.getUserByIdName(idName));
  }

  @PatchMapping(UrlConstant.User.UPDATE)
  public ResponseEntity<?> updateUser(@ModelAttribute UpdateUserInput updateUserInput) {
    return VsResponseUtil.ok(userService.updateUser(updateUserInput));
  }

  @PostMapping(UrlConstant.User.CHANGE_AVATAR)
  public ResponseEntity<?> changeAvatarUser(@ModelAttribute ChangeAvatarInput changeAvatarInput) {
    return VsResponseUtil.ok(userService.changeAvatar(changeAvatarInput));
  }

  @PostMapping(UrlConstant.User.FOLLOW)
  public ResponseEntity<?> follow(@Valid @PathVariable("idFollow") Long idFollow) {
    return VsResponseUtil.ok(userService.follow(idFollow));
  }

  @PostMapping(UrlConstant.User.UNFOLLOW)
  public ResponseEntity<?> unfollow(@Valid @PathVariable("idFollow") Long idFollow) {
    return VsResponseUtil.ok(userService.unfollow(idFollow));
  }

  @DeleteMapping(UrlConstant.User.DELETE)
  public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
    return VsResponseUtil.ok(userService.deleteById(id));
  }
  
}
