package com.tamas.ravasz.learning.lift.service;

import com.tamas.ravasz.learning.lift.constants.LiftConstants;
import com.tamas.ravasz.learning.lift.domain.lift.Lift;
import com.tamas.ravasz.learning.lift.domain.floor.Floor;
import com.tamas.ravasz.learning.lift.domain.requests.ExternalRequest;
import com.tamas.ravasz.learning.lift.domain.requests.InternalRequest;
import com.tamas.ravasz.learning.lift.dto.ExternalRequestDto;
import com.tamas.ravasz.learning.lift.dto.InternalRequestDto;
import com.tamas.ravasz.learning.lift.dto.LiftSystemConfig;
import com.tamas.ravasz.learning.lift.exception.FloorNotFoundException;
import com.tamas.ravasz.learning.lift.exception.LiftNotFoundException;
import com.tamas.ravasz.learning.lift.strategy.LiftSelectionStrategy;
import com.tamas.ravasz.learning.lift.validator.LiftValidator;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LiftSystem {

  private final LiftValidator liftValidator;
  private final LiftSelectionStrategy selectionStrategy;
  private final FloorService floorService;
  private final LiftService liftService;

  @Getter private final LiftSystemConfig liftSystemConfig;

  public LiftSystem(
      LiftValidator liftValidator,
      LiftSelectionStrategy selectionStrategy,
      FloorService floorService,
      LiftService liftService) {
    this.liftValidator = liftValidator;
    this.selectionStrategy = selectionStrategy;
    this.floorService = floorService;
    this.liftService = liftService;

    this.liftSystemConfig =
        LiftSystemConfig.builder()
            .minimumFloor(LiftConstants.MINIMUM_FLOOR)
            .maximumFloor(LiftConstants.MAXIMUM_FLOOR)
            .build();
  }

  public void callLiftToFloor(ExternalRequestDto elevatorCall) {
    liftValidator.validateExternalRequest(elevatorCall);
    log.info(
        "Calling in direction"
            + elevatorCall.getDirection()
            + " + lift to floor"
            + elevatorCall.getTargetFloor());

    try {
      Lift lift = selectionStrategy.selectLift(liftService.getAllLifts(), elevatorCall);

      Floor floor = floorService.findFloorByFloorNumber(elevatorCall.getTargetFloor());

      floor.pressDirectionButton(elevatorCall.getDirection());

      lift.addExternalRequest(
          ExternalRequest.builder()
              .targetFloor(elevatorCall.getTargetFloor())
              .direction(elevatorCall.getDirection())
              .build());

    } catch (LiftNotFoundException | FloorNotFoundException e) {
      log.error(e.getMessage());
    }
  }

  public void callFromTheLiftPanel(InternalRequestDto elevatorCall) {
    log.info("Incoming request: {}", elevatorCall.getLiftId());
    liftValidator.validateInternalRequest(elevatorCall);

    try {
      Lift lift = liftService.findLiftById(elevatorCall.getLiftId());

      log.info("Found elevator: {}", lift.getId());

      lift.pressButton(elevatorCall.getTargetFloor());

      lift.addInternalRequest(
          InternalRequest.builder().targetFloor(elevatorCall.getTargetFloor()).build());

    } catch (LiftNotFoundException e) {
      log.error(e.getMessage());
    }
  }
}
