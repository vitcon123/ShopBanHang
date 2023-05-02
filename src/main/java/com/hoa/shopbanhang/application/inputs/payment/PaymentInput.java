package com.hoa.shopbanhang.application.inputs.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInput {

  private int amount;

  private String orderInfo;

  private String bankCode;
}
