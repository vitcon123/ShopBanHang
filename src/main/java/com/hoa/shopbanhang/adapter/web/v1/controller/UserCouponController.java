package com.hoa.shopbanhang.adapter.web.v1.controller;

import com.hoa.shopbanhang.adapter.web.base.RestApiV1;
import com.hoa.shopbanhang.adapter.web.base.VsResponseUtil;
import com.hoa.shopbanhang.application.constants.UrlConstant;
import com.hoa.shopbanhang.application.inputs.coupon.AddCouponToUsersInput;
import com.hoa.shopbanhang.application.inputs.coupon.CreateCouponInput;
import com.hoa.shopbanhang.application.inputs.coupon.DeleteCouponToUsersInput;
import com.hoa.shopbanhang.application.inputs.coupon.UpdateCouponInput;
import com.hoa.shopbanhang.application.services.IUserCouponService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestApiV1
public class UserCouponController {

  private final IUserCouponService userCouponService;

  public UserCouponController(IUserCouponService userCouponService) {
    this.userCouponService = userCouponService;
  }

  @Operation(summary = "Add Coupon To Users - ADMIN")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping(UrlConstant.UserCoupon.ADD_COUPON_TO_USERS)
  public ResponseEntity<?> addCouponToUsers(@RequestBody AddCouponToUsersInput input) {
    return VsResponseUtil.ok(userCouponService.addCouponToUsers(input));
  }

  @Operation(summary = "Delete Coupon To Users - ADMIN")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping(UrlConstant.UserCoupon.DELETE_COUPON_TO_USERS)
  public ResponseEntity<?> deleteCouponToUsers(@RequestBody DeleteCouponToUsersInput input) {
    return VsResponseUtil.ok(userCouponService.deleteCouponToUsers(input));
  }

}
