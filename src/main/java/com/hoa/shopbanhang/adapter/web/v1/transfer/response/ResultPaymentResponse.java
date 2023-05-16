package com.hoa.shopbanhang.adapter.web.v1.transfer.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResultPaymentResponse {

  private String orderInfo;

  private Long amount;

  private String message;

  private String bankCode;
}
