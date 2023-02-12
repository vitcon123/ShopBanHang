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

  private String name;

  private String email;

  private String birthday;

  private String phone;

  private String address;

  private String school;

  private String gender;

  private String grade;

  private String faculty;

}
