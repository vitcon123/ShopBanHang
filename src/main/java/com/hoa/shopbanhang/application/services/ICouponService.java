package com.hoa.shopbanhang.application.services;

import com.hoa.shopbanhang.adapter.web.v1.transfer.response.RequestResponse;
import com.hoa.shopbanhang.application.inputs.coupon.CreateCouponInput;
import com.hoa.shopbanhang.application.inputs.coupon.UpdateCouponInput;
import com.hoa.shopbanhang.domain.entities.Coupon;
import com.hoa.shopbanhang.domain.entities.UserCoupon;

import java.util.List;

public interface ICouponService {
  List<Coupon> getAll();
  List<UserCoupon> getAllCouponOfUser(Long userId);

  Coupon getCouponById(Long id);

  Coupon createCoupon(CreateCouponInput createCouponInput);

  Coupon updateCoupon(UpdateCouponInput updateCouponInput);

  RequestResponse deleteById(Long id);
}
