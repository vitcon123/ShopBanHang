package com.hoa.shopbanhang.application.services;

import com.hoa.shopbanhang.adapter.web.v1.transfer.response.PaymentResponse;
import com.hoa.shopbanhang.application.inputs.payment.PaymentInput;
import org.springframework.stereotype.Service;

@Service
public interface IPaymentService {

  PaymentResponse paymentOrder(PaymentInput paymentInput, String orderId);
}
