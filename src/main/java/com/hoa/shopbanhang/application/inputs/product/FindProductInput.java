package com.hoa.shopbanhang.application.inputs.product;

import com.hoa.shopbanhang.application.inputs.common.PageMetaInput;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FindProductInput extends PageMetaInput {

  private Long idCategory;

  private String name;

  private Double price;

  private Integer stock;

  private String brand;

}
