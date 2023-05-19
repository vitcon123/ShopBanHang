package com.hoa.shopbanhang.adapter.web.v1.controller;

import com.hoa.shopbanhang.adapter.web.base.RestApiV1;
import com.hoa.shopbanhang.adapter.web.base.VsResponseUtil;
import com.hoa.shopbanhang.adapter.web.v1.transfer.response.PaymentResponse;
import com.hoa.shopbanhang.application.inputs.payment.PaymentInput;
import com.hoa.shopbanhang.application.services.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestApiV1
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/payment/{orderId}/payment-order")
    public ResponseEntity<?> paymentOrder(@RequestBody PaymentInput paymentInput,
                                          @PathVariable(name = "orderId") String orderId) {
        PaymentResponse paymentResponse = paymentService.paymentOrder(paymentInput, orderId);
        return VsResponseUtil.ok(paymentResponse);
    }

    @GetMapping("/payment/info-details")
    public ResponseEntity<?> resultPaymentOrder(@RequestParam(name = "vnp_Amount", required = false) Integer vnp_Amount,
                                                @RequestParam(name = "vnp_BankCode", required = false) String vnp_BankCode,
                                                @RequestParam(name = "vnp_BankTranNo", required = false) String vnp_BankTranNo,
                                                @RequestParam(name = "vnp_CardType", required = false) String vnp_CardType,
                                                @RequestParam(name = "vnp_OrderInfo", required = false) String vnp_OrderInfo,
                                                @RequestParam(name = "vnp_PayDate", required = false) Long vnp_PayDate,
                                                @RequestParam(name = "vnp_ResponseCode", required = false) Integer vnp_ResponseCode,
                                                @RequestParam(name = "vnp_TmnCode", required = false) String vnp_TmnCode,
                                                @RequestParam(name = "vnp_TransactionNo", required = false) Integer vnp_TransactionNo,
                                                @RequestParam(name = "vnp_TransactionStatus", required = false) Integer vnp_TransactionStatus,
                                                @RequestParam(name = "vnp_TxnRef", required = false) String vpn_TxnRef,
                                                @RequestParam(name = "vnp_SecureHash", required = false) String vnp_SecureHash){
        return VsResponseUtil.ok(paymentService.resultPaymentOrder(vnp_Amount, vnp_BankCode, vnp_BankTranNo, vnp_CardType, vnp_OrderInfo,
            vnp_PayDate, vnp_ResponseCode, vnp_TmnCode, vnp_TransactionNo, vnp_TransactionStatus, vpn_TxnRef, vnp_SecureHash));
    }
}
