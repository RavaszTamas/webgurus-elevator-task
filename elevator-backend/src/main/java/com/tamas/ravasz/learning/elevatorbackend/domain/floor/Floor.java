package com.tamas.ravasz.learning.elevatorbackend.domain.floor;

import com.tamas.ravasz.learning.elevatorbackend.domain.Elevator;
import com.tamas.ravasz.learning.elevatorbackend.domain.enums.Direction;
import com.tamas.ravasz.learning.elevatorbackend.domain.panels.FloorButtonPanel;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Floor {
  private int floorNumber;
  private Map<String, Direction> elevatorDirectionIndicators;
  private FloorButtonPanel floorButtonPanel;

  public Floor(int floorNumber, Map<String, Elevator> elevators) {
    this.floorNumber = floorNumber;
    this.elevatorDirectionIndicators = new HashMap<>();
    for (String elevatorId : elevators.keySet()) {
      elevatorDirectionIndicators.put(elevatorId, Direction.STOPPED);
    }
    floorButtonPanel = new FloorButtonPanel();
  }

  public void callDirection(Direction direction){
    if(direction == Direction.UP){
      floorButtonPanel.moveUp();

    }
    else if(direction == Direction.DOWN){
      floorButtonPanel.moveDown();
    }
  }

  public void updateIndicators(Map<String,Elevator> elevatorMap) {
    for(Elevator elevator : elevatorMap.values()) {
      elevatorDirectionIndicators.put(elevator.getId(), elevator.getDirection());
    }
  }

}
