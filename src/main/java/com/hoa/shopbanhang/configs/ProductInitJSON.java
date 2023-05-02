package com.hoa.shopbanhang.configs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductInitJSON {

  private String name;

  private String description;

  private Double price;

  private Integer stock;

  private String brand;

  private Long category;

  private String slug;

  private List<String> images;

}
