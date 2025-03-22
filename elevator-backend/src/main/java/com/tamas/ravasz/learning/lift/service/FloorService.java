package com.tamas.ravasz.learning.lift.service;

import com.tamas.ravasz.learning.lift.constants.LiftConstants;
import com.tamas.ravasz.learning.lift.domain.Lift;
import com.tamas.ravasz.learning.lift.domain.floor.Floor;
import com.tamas.ravasz.learning.lift.exception.FloorNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class FloorService {
  private final HashMap<Integer, Floor> floors;

  public FloorService() {
    floors = new HashMap<>();

    for (int i = 0; i <= LiftConstants.MAXIMUM_FLOOR; i++) {
      floors.put(i, new Floor(i));
    }
  }

  public void updateFloorIndicators(List<Lift> lifts) {
    for (Floor floor : floors.values()) {
      floor.updateIndicators(lifts);
    }
  }

  public Floor findFloorByFloorNumber(int floor) throws FloorNotFoundException {
    return Optional.ofNullable(floors.get(floor))
        .orElseThrow(
            () -> new FloorNotFoundException("Floor with floor number " + floor + " not found"));
  }

  public List<Floor> getFloors() {
    return floors.values().stream().toList();
  }
}
