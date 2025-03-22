package com.tamas.ravasz.learning.elevatorbackend.config;

import com.tamas.ravasz.learning.elevatorbackend.exception.InvalidPayloadException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ElevatorExceptionHandler {

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(InvalidPayloadException.class)
    public void handleConflict(Exception ex) {}

}
