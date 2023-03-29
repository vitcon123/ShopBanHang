package com.hoa.shopbanhang.application.inputs.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchProductInput {

  private Long idCategory;

  private String name;

  private Double price;

  private Integer stock;

  private String brand;
}
