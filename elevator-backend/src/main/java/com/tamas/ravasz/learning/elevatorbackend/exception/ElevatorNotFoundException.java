package com.tamas.ravasz.learning.elevatorbackend.exception;

public class ElevatorNotFoundException extends RuntimeException {
  public ElevatorNotFoundException(String message) {
    super(message);
  }
}
