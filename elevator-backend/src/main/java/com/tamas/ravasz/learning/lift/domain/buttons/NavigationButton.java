package com.tamas.ravasz.learning.lift.domain.buttons;

import com.tamas.ravasz.learning.lift.domain.enums.Direction;
import lombok.*;

/** Button that is located on a floor. Used to call an elevator to that floor */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NavigationButton implements Button {
  private boolean pressed;
  private Direction direction;

  @Override
  public void pressButton() {
    pressed = true;
  }

  @Override
  public void releaseButton() {
    this.pressed = false;
  }
}
