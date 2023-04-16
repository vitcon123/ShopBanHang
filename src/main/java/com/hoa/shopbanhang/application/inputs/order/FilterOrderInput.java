package com.hoa.shopbanhang.application.inputs.order;

import com.hoa.shopbanhang.application.constants.DeliveryStatus;
import com.hoa.shopbanhang.application.constants.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FilterOrderInput {

  private DeliveryStatus deliveryStatus;

  private PaymentMethod paymentMethod;

}
