package com.hoa.shopbanhang.application.inputs.media;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMediaInput {

  private Long id;

  private String name;

  private String description;
}
