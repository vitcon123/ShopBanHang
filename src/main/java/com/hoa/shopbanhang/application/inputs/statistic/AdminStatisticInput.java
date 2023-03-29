package com.hoa.shopbanhang.application.inputs.statistic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminStatisticInput {

  private Integer ageMin;

  private Integer ageMax;

  private String timeStart;

  private String timeEnd;

}
