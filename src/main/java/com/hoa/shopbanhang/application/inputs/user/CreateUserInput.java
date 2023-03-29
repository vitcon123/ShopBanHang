package com.hoa.shopbanhang.application.inputs.user;

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
public class CreateUserInput {

  @NotBlank(message = MessageConstant.INVALID_SOME_THING_FIELD_IS_REQUIRED)
  private String name;

  @NotBlank(message = MessageConstant.INVALID_SOME_THING_FIELD_IS_REQUIRED)
  private String email;

  @NotBlank(message = MessageConstant.INVALID_SOME_THING_FIELD_IS_REQUIRED)
  private String password;

}
