package com.hoa.shopbanhang.adapter.web.v1.transfer.parameter.auth;

import com.hoa.shopbanhang.application.constants.MessageConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthenticationRequest implements Serializable {

  @NotBlank(message = MessageConstant.INVALID_SOME_THING_FIELD_IS_REQUIRED)
  private String email;

  @NotBlank(message = MessageConstant.INVALID_SOME_THING_FIELD_IS_REQUIRED)
  private String password;

}
