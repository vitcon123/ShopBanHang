package com.hoa.shopbanhang.application.services;

import com.hoa.shopbanhang.adapter.web.v1.transfer.response.RequestResponse;
import com.hoa.shopbanhang.application.inputs.coupon.AddCouponToUsersInput;
import com.hoa.shopbanhang.application.inputs.coupon.DeleteCouponToUsersInput;

public interface IUserCouponService {
  RequestResponse addCouponToUsers(AddCouponToUsersInput input);
  RequestResponse deleteCouponToUsers(DeleteCouponToUsersInput input);
}
