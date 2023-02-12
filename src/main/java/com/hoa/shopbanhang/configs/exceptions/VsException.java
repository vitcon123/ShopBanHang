package com.hoa.shopbanhang.configs.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class VsException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private HttpStatus status;
  private String userMessage;
  private String devMessage;
  private String[] params;

  public VsException(String devMessage) {
    super(devMessage);
    this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    this.devMessage = devMessage;
  }

  public VsException(String userMessage, String devMessage) {
    super(userMessage);
    this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    this.userMessage = userMessage;
    this.devMessage = devMessage;
  }

  public VsException(HttpStatus status, String userMessage, String devMessage) {
    super(userMessage);
    this.status = status;
    this.userMessage = userMessage;
    this.devMessage = devMessage;
  }

  public VsException(HttpStatus status, String userMessage, String devMessage, String[] params) {
    super(userMessage);
    this.status = status;
    this.userMessage = userMessage;
    this.devMessage = devMessage;
    this.params = params;
  }

  public VsException(String userMessage, String devMessage, String[] params) {
    super(userMessage);
    this.userMessage = userMessage;
    this.devMessage = devMessage;
    this.params = params;
  }

}