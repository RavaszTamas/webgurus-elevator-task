package com.tamas.ravasz.learning.lift.strategy;

import com.tamas.ravasz.learning.lift.domain.Lift;
import com.tamas.ravasz.learning.lift.dto.ExternalRequestDto;
import com.tamas.ravasz.learning.lift.exception.LiftNotFoundException;
import java.util.List;

public interface LiftSelectionStrategy {
  Lift selectLift(List<Lift> lifts, ExternalRequestDto elevatorCall) throws LiftNotFoundException;
}
