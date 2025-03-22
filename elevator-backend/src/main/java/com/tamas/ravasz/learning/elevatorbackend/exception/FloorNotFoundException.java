package com.tamas.ravasz.learning.elevatorbackend.exception;

public class FloorNotFoundException extends RuntimeException {
  public FloorNotFoundException(String message) {
    super(message);
  }
}
