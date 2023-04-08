package com.hoa.shopbanhang.adapter.web.v1.transfer.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOutput {

  private Long id;

  private String name;

  private String description;

  private Double price;

  private Double rating = 0.0;

  private Integer stock;

  private String brand;

  private String category;

  private String thumbnail;

  private String slug;

  private List<String> images;

}
