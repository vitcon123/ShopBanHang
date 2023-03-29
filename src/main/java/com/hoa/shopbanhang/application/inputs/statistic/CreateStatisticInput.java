package com.hoa.shopbanhang.application.inputs.statistic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateStatisticInput {

  private Integer ageOfUser;

  private Long idUser;

  private Long idProduct;

}
