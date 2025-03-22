package com.tamas.ravasz.learning.elevatorbackend.validator;

import com.tamas.ravasz.learning.elevatorbackend.constants.ElevatorConstants;
import com.tamas.ravasz.learning.elevatorbackend.dto.ExternalRequestDto;
import com.tamas.ravasz.learning.elevatorbackend.dto.InternalRequestDto;
import com.tamas.ravasz.learning.elevatorbackend.exception.InvalidPayloadException;
import org.springframework.stereotype.Component;

@Component
public class ElevatorValidator {

  public void validateExternalRequest(ExternalRequestDto externalRequestDto) {
    if (externalRequestDto.getTargetFloor() < 0
        || externalRequestDto.getTargetFloor() > ElevatorConstants.MAXIMUM_FLOOR) {
      throw new InvalidPayloadException(
          "Target floor must be between 0 and " + ElevatorConstants.MAXIMUM_FLOOR);
    }
  }
  public void validateInternalRequest(InternalRequestDto internalRequestDto) {
    if (internalRequestDto.getTargetFloor() < 0
        || internalRequestDto.getTargetFloor() > ElevatorConstants.MAXIMUM_FLOOR) {
      throw new InvalidPayloadException(
          "Target floor must be between 0 and " + ElevatorConstants.MAXIMUM_FLOOR);
    }
  }
}
