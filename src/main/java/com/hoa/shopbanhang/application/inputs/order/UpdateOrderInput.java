package com.hoa.shopbanhang.application.inputs.order;

import com.hoa.shopbanhang.application.constants.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderInput {

  private Long idOrder;

  private DeliveryStatus deliveryStatus;

}
