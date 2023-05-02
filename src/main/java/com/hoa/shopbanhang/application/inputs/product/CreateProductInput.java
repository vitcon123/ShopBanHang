package com.hoa.shopbanhang.application.inputs.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductInput {

  private Long idCategory;

  private String name;

  private Double price;

  private Integer stock;

  private String description;

  private String brand;

  private List<MultipartFile> images;

}
