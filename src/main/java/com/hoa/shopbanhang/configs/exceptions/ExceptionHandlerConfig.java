package com.hoa.shopbanhang.configs.exceptions;

import com.hoa.shopbanhang.adapter.web.base.RestData;
import com.hoa.shopbanhang.adapter.web.base.VsResponseUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ExceptionHandlerConfig {

  private static final Log LOG = LogFactory.getLog(ExceptionHandlerConfig.class);

  @ExceptionHandler(InvalidException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<RestData<?>> handleNotFoundException(InvalidException ex) {
    return VsResponseUtil.error(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
  }

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<RestData<?>> handleNotFoundException(NotFoundException ex) {
    return VsResponseUtil.error(HttpStatus.NOT_FOUND, ex.getMessage());
  }

  @ExceptionHandler(VsException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<RestData<?>> handleNotFoundException(VsException ex) {
    return VsResponseUtil.error(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
  }

  @ExceptionHandler(BindException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<RestData<?>> handleBindException(BindException e) {
    List<String> message = new ArrayList<>();
    for (ObjectError o : e.getBindingResult().getAllErrors()) {
      message.add(o.getDefaultMessage());
    }
    return VsResponseUtil.error(HttpStatus.BAD_REQUEST, message);
  }

}
