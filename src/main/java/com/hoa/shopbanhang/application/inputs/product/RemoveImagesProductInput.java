package com.hoa.shopbanhang.application.inputs.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RemoveImagesProductInput {

  private Long id;

  private List<String> images;

}
