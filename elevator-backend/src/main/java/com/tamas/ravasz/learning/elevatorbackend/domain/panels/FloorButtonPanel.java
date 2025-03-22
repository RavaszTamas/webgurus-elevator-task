package com.tamas.ravasz.learning.elevatorbackend.domain.panels;

import com.tamas.ravasz.learning.elevatorbackend.domain.buttons.NavigationButton;
import com.tamas.ravasz.learning.elevatorbackend.domain.enums.Direction;
import java.util.HashMap;
import lombok.Getter;
import lombok.Setter;

/** Button panel located on a floor. */
@Getter
@Setter
public class FloorButtonPanel {
  private final HashMap<Direction, NavigationButton> navigationButtons;

  public FloorButtonPanel() {
    navigationButtons = new HashMap<>();
    navigationButtons.put(Direction.UP, new NavigationButton(false, Direction.UP));
    navigationButtons.put(Direction.DOWN, new NavigationButton(false, Direction.DOWN));
  }


  public void press(Direction direction) {
    navigationButtons.get(direction).pressButton();
  }

  public void clearButton(Direction direction) {
    navigationButtons.get(direction).setPressed(false);
  }

  public void moveUp() {
    press(Direction.UP);
  }

  public void moveDown() {
    press(Direction.DOWN);
  }

  public void clearUpButton() {
    clearButton(Direction.UP);
  }

  public void clearDownButton() {
    clearButton(Direction.DOWN);
  }
}
