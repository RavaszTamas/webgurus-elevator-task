package com.tamas.ravasz.learning.lift.config;

import com.tamas.ravasz.learning.lift.exception.InvalidPayloadException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class LiftExceptionHandler {

  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  @ExceptionHandler(InvalidPayloadException.class)
  public void handlePayloadException(Exception ex) {}
}
