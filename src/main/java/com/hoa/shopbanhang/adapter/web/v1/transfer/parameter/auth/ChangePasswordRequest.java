package com.hoa.shopbanhang.adapter.web.v1.transfer.parameter.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChangePasswordRequest {

  private String email;

  private String oldPassword;

  private String newPassword;
}
