package com.hoa.shopbanhang.application.inputs.order;

import com.hoa.shopbanhang.application.constants.DeliveryMethod;
import com.hoa.shopbanhang.application.constants.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderInput {

  private Long idUser;

  private String fullName;

  private String address;

  private String phone;

  private LocalDateTime orderedDate = LocalDateTime.now();

  private String deliveredDate;

  private DeliveryMethod deliveryMethod;

  private PaymentMethod paymentMethod;

  private List<Long> idItemDetails;

}
