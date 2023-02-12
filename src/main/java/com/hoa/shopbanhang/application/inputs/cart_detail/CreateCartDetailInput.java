package com.hoa.shopbanhang.application.inputs.cart_detail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCartDetailInput {

  private String name;

  private String description;
}
