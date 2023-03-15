package com.hoa.shopbanhang.application.inputs.item_detail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateItemDetailInput {

  private Long id;

  private String name;

  private String description;
}
