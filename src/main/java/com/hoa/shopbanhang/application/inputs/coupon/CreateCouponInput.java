package com.hoa.shopbanhang.application.inputs.coupon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCouponInput {

  private String code;

  private Long amountPlan;

  private Long amountUsed = 0L;

  private Double value;

}
