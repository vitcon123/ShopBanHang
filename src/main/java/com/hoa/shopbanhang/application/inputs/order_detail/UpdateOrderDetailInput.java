package com.hoa.shopbanhang.application.inputs.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderDetailInput {

  private Long id;

  private String name;

  private String description;
}
