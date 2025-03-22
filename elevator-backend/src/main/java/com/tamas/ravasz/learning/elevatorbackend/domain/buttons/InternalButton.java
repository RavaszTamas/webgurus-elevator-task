package com.tamas.ravasz.learning.elevatorbackend.domain.buttons;

import lombok.*;

/**
 * An internal button located inside the elevator.
 * Used for requesting the elevator to a specific floor
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InternalButton implements Button {

  private int targetFloor;
  private boolean isPressed;

  @Override
  public void pressButton() {
    this.isPressed = !isPressed;
  }

  @Override
  public void releaseButton() {
    this.isPressed = false;
  }
}
