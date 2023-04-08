package com.hoa.shopbanhang.adapter.web.v1.transfer.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ElementCollection;
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

  private Double rating;

  private Integer stock;

  private String brand;

  private String category;

  private String thumbnail;

  private String slug;

  @ElementCollection
  private List<String> images;


}
