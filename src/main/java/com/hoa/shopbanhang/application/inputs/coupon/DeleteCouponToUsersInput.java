package com.hoa.shopbanhang.application.inputs.coupon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteCouponToUsersInput {

  private List<Long> userIds = new ArrayList<>();

  private Long couponId;

}
