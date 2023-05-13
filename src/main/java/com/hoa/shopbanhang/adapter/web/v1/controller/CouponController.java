package com.hoa.shopbanhang.adapter.web.v1.controller;

import com.hoa.shopbanhang.adapter.web.base.RestApiV1;
import com.hoa.shopbanhang.adapter.web.base.VsResponseUtil;
import com.hoa.shopbanhang.application.constants.UrlConstant;
import com.hoa.shopbanhang.application.inputs.coupon.CreateCouponInput;
import com.hoa.shopbanhang.application.inputs.coupon.UpdateCouponInput;
import com.hoa.shopbanhang.application.services.ICouponService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestApiV1
public class CouponController {

  private final ICouponService couponService;

  public CouponController(ICouponService couponService) {
    this.couponService = couponService;
  }


  @Operation(summary = "Get All Coupon")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping(UrlConstant.Coupon.LIST)
  public ResponseEntity<?> getAll() {
    return VsResponseUtil.ok(couponService.getAll());
  }

  @Operation(summary = "Get All Coupon Of User")
  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
  @GetMapping(UrlConstant.Coupon.GET_ALL_COUPON_OF_USER)
  public ResponseEntity<?> getAll(@PathVariable("userId") Long userId) {
    return VsResponseUtil.ok(couponService.getAllCouponOfUser(userId));
  }

  @Operation(summary = "Get Coupon By Id")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping(UrlConstant.Coupon.GET)
  public ResponseEntity<?> getCouponById(@PathVariable("id") Long id) {
    return VsResponseUtil.ok(couponService.getCouponById(id));
  }

  @Operation(summary = "Create Coupon - ADMIN")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping(UrlConstant.Coupon.CREATE)
  public ResponseEntity<?> createCoupon(@RequestBody CreateCouponInput createCouponInput) {
    return VsResponseUtil.ok(couponService.createCoupon(createCouponInput));
  }

  @Operation(summary = "Update Coupon - ADMIN")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PatchMapping(UrlConstant.Coupon.UPDATE)
  public ResponseEntity<?> updateCoupon(@RequestBody UpdateCouponInput updateCouponInput) {
    return VsResponseUtil.ok(couponService.updateCoupon(updateCouponInput));
  }

  @Operation(summary = "Delete Coupon - ADMIN")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @DeleteMapping(UrlConstant.Coupon.DELETE)
  public ResponseEntity<?> deleteCoupon(@PathVariable("id") Long id) {
    return VsResponseUtil.ok(couponService.deleteById(id));
  }

}
