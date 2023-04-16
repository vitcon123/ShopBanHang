package com.hoa.shopbanhang.application.inputs.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserInput {

  private Long id;

  private String fullName;

  private String email;

  private String password;

  private String birthday;

  private String phone;

  private String address;

  private String gender;

  private String avatar;

}
