package com.tamas.ravasz.learning.lift.service;

import com.tamas.ravasz.learning.lift.constants.LiftConstants;
import com.tamas.ravasz.learning.lift.domain.lift.Lift;
import com.tamas.ravasz.learning.lift.exception.LiftNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class LiftService {
  private final Map<String, Lift> lifts;

  public LiftService() {
    lifts = new HashMap<>();
    lifts.put("A", new Lift("A", LiftConstants.MINIMUM_FLOOR));
    lifts.put("B", new Lift("B", LiftConstants.MAXIMUM_FLOOR));
  }

  public List<Lift> getAllLifts() {
    return lifts.values().stream().toList();
  }

  public Lift findLiftById(String id) throws LiftNotFoundException {
    return Optional.ofNullable(lifts.get(id))
        .orElseThrow(() -> new LiftNotFoundException("Lift with id " + id + " not found"));
  }

  public void moveLifts() {
    for (Lift lift : lifts.values()) {
      lift.move();
      lift.updateLiftDisplay();
    }
  }
}
