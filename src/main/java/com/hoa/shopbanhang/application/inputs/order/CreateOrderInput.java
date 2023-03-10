package com.hoa.shopbanhang.application.inputs.order;

import com.hoa.shopbanhang.application.constants.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderInput {

  private Long idUser;

  private String deliveredDate;

  private PaymentMethod paymentMethod;

  private List<Long> idItemDetails;

}
