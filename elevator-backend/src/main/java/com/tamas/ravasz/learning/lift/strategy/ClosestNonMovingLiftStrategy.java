package com.tamas.ravasz.learning.lift.strategy;

import com.tamas.ravasz.learning.lift.domain.lift.Lift;
import com.tamas.ravasz.learning.lift.domain.enums.Direction;
import com.tamas.ravasz.learning.lift.dto.ExternalRequestDto;
import com.tamas.ravasz.learning.lift.exception.LiftNotFoundException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ClosestNonMovingLiftStrategy implements LiftSelectionStrategy {
  @Override
  public Lift selectLift(List<Lift> lifts, ExternalRequestDto elevatorCall)
      throws LiftNotFoundException {
    log.info(
        "Finding best elevator for floor by searching for the closest"
            + elevatorCall.getTargetFloor());
    Lift closestLift = null;

    int minDistance = Integer.MAX_VALUE;

    for (Lift lift : lifts) {

      int distance = Math.abs(elevatorCall.getTargetFloor() - lift.getCurrentFloor());

      if (lift.getDirection() != Direction.STOPPED) {
        // If the elevator is busy we increase the distance
        distance *= 2;
      }

      if (distance < minDistance) {
        closestLift = lift;
        minDistance = distance;
      } else if (distance == minDistance && closestLift != null) {
        if (lift.getCurrentFloor() < closestLift.getCurrentFloor()) {
          closestLift = lift;
        }
      }
    }
    if (closestLift == null) {
      throw new LiftNotFoundException("Lift not found");
    }

    log.info("Found best elevator for " + closestLift.getId());
    return closestLift;
  }
}
