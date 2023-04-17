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
public class UpdateUserInput {

  private Long id;

  private String fullName;

  private String birthday;

  private String phone;

  private String address;

  private String gender;

}
