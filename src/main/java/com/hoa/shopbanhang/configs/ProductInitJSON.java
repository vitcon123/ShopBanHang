package com.hoa.shopbanhang.configs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ElementCollection;
import javax.validation.constraints.NotBlank;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductInitJSON {
  private Long category;

  private String name;

  private String slug;

  private Double price;

  private Integer stock;

  private String description;

  private String brand;

  private List<String> images;

}
