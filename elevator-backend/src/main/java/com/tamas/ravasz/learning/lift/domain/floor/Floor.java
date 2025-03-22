package com.tamas.ravasz.learning.lift.domain.floor;

import com.tamas.ravasz.learning.lift.domain.Lift;
import com.tamas.ravasz.learning.lift.domain.enums.Direction;
import com.tamas.ravasz.learning.lift.domain.enums.LiftState;
import com.tamas.ravasz.learning.lift.domain.panels.FloorButtonPanel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Floor {
  private int floorNumber;
  private Map<String, Direction> liftDirectionIndicators;
  private FloorButtonPanel floorButtonPanel;

  public Floor(int floorNumber) {
    this.floorNumber = floorNumber;
    this.liftDirectionIndicators = new HashMap<>();
    floorButtonPanel = new FloorButtonPanel();
  }

  /**
   * Call the change the state of the pressed button
   *
   * @param direction
   */
  public void callDirection(Direction direction) {
    if (direction == Direction.UP) {
      floorButtonPanel.moveUp();

    } else if (direction == Direction.DOWN) {
      floorButtonPanel.moveDown();
    }
  }

  /**
   * Set the movement indicator and the pressed buttons are released.
   *
   * @param lifts the collection storing the elevators
   */
  public void updateIndicators(List<Lift> lifts) {
    for (Lift lift : lifts) {
      liftDirectionIndicators.put(lift.getId(), lift.getDirection());
      // If the elevator is on this floor and it is stopped
      if (lift.getCurrentFloor() == floorNumber && lift.getState() == LiftState.STOPPED) {
        floorButtonPanel.clearUpButton();
        floorButtonPanel.clearDownButton();
      }
    }
  }
}
