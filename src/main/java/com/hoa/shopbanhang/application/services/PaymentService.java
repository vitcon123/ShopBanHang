package com.hoa.shopbanhang.application.services;

import com.hoa.shopbanhang.adapter.web.v1.transfer.response.PaymentResponse;
import com.hoa.shopbanhang.adapter.web.v1.transfer.response.ResultPaymentResponse;
import com.hoa.shopbanhang.application.inputs.payment.PaymentInput;

public interface PaymentService {
  PaymentResponse paymentOrder(PaymentInput paymentInput, String orderId);

  ResultPaymentResponse resultPaymentOrder(Integer vnp_Amount,
                                           String vnp_BankCode,
                                           String vnp_BankTranNo,
                                           String vnp_CardType,
                                           String vnp_OrderInfo,
                                           Long vnp_PayDate,
                                           Integer vnp_ResponseCode,
                                           String vnp_TmnCode,
                                           Integer vnp_TransactionNo,
                                           Integer vnp_TransactionStatus,
                                           String vpn_TxnRef,
                                           String vnp_SecureHash);
}
