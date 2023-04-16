package com.hoa.shopbanhang.adapter.web.v1.controller;

import com.hoa.shopbanhang.adapter.web.base.RestApiV1;
import com.hoa.shopbanhang.adapter.web.base.VsResponseUtil;
import com.hoa.shopbanhang.application.inputs.payment.PaymentInput;
import com.hoa.shopbanhang.application.services.IPaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestApiV1
public class PaymentController {

  private final IPaymentService paymentService;

  public PaymentController(IPaymentService paymentService) {
    this.paymentService = paymentService;
  }

  @PostMapping("/payment/{orderId}/payment-order")
  public ResponseEntity<?> paymentOrder(@RequestBody PaymentInput paymentInput,
                                        @PathVariable(name = "orderId") String orderId) {
    return VsResponseUtil.ok(paymentService.paymentOrder(paymentInput, orderId));
  }
}
