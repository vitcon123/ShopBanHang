package com.vitweb.vitwebapi.adapter.web.v1.transfer.parameter.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshPasswordRequest {

  private String email;

  private String newPassword;
}
