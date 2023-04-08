package com.hoa.shopbanhang.adapter.web.v1.transfer.parameter.auth;

import com.hoa.shopbanhang.application.constants.MessageConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePasswordInput {

  @NotBlank(message = MessageConstant.INVALID_SOME_THING_FIELD_IS_REQUIRED)
  private String email;

  @NotBlank(message = MessageConstant.INVALID_SOME_THING_FIELD_IS_REQUIRED)
  private String token;

  @NotBlank(message = MessageConstant.INVALID_SOME_THING_FIELD_IS_REQUIRED)
  private String newPassword;

}
