package com.hoa.shopbanhang.application.inputs.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePasswordInput {

  private Long id;

  private String oldPassword;

  private String newPassword;

}
