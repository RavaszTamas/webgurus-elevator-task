package com.tamas.ravasz.learning.lift.domain.buttons;

import lombok.*;

/**
 * An internal button located inside the elevator. Used for requesting the elevator to a specific
 * floor
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InternalButton implements Button {

  private int targetFloor;
  private boolean pressed;

  @Override
  public void pressButton() {
    this.pressed = !pressed;
  }

  @Override
  public void releaseButton() {
    this.pressed = false;
  }
}
