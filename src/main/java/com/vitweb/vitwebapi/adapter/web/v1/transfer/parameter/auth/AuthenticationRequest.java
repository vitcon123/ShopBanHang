package com.vitweb.vitwebapi.adapter.web.v1.transfer.parameter.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthenticationRequest implements Serializable {

  private String email;

  private String password;

}
