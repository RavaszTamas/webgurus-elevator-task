package com.tamas.ravasz.learning.elevatorbackend.exception;

public class InvalidPayloadException extends RuntimeException {
  public InvalidPayloadException(String message) {
    super(message);
  }
}
