package com.hoa.shopbanhang.application.inputs.rate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateRateInput {

  private Long idUser;

  private Long idProduct;

  private Integer star;

  private String review;
}
