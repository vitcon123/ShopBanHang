package com.hoa.shopbanhang.adapter.web.v1.transfer.parameter.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GetAllDataParameter {

  @Min(value = 0, message = "invalid.data.page.min")
  private Long page = 0L;

  private Boolean activeFlag;

  private Boolean both;

}
