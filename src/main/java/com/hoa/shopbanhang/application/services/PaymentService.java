package com.hoa.shopbanhang.application.services;

import com.hoa.shopbanhang.adapter.web.v1.transfer.response.PaymentResponse;
import com.hoa.shopbanhang.application.inputs.payment.PaymentInput;

public interface PaymentService {
    PaymentResponse paymentOrder(PaymentInput paymentInput, String orderId);

}
