package com.hoa.shopbanhang.application.inputs.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCartInput {

  private Long id;

  private String name;

  private String description;
}
