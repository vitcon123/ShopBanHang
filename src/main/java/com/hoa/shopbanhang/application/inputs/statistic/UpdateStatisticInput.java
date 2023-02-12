package com.hoa.shopbanhang.application.inputs.statistic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStatisticInput {

  private Long id;

  private String name;

  private String description;
}
