package com.tamas.ravasz.learning.lift.validator;

import com.tamas.ravasz.learning.lift.constants.LiftConstants;
import com.tamas.ravasz.learning.lift.dto.ExternalRequestDto;
import com.tamas.ravasz.learning.lift.dto.InternalRequestDto;
import com.tamas.ravasz.learning.lift.exception.InvalidPayloadException;
import org.springframework.stereotype.Component;

@Component
public class LiftValidator {

  public void validateExternalRequest(ExternalRequestDto externalRequestDto) {
    if (externalRequestDto.getTargetFloor() < 0
        || externalRequestDto.getTargetFloor() > LiftConstants.MAXIMUM_FLOOR) {
      throw new InvalidPayloadException(
          "Target floor must be between 0 and " + LiftConstants.MAXIMUM_FLOOR);
    }
  }

  public void validateInternalRequest(InternalRequestDto internalRequestDto) {
    if (internalRequestDto.getTargetFloor() < 0
        || internalRequestDto.getTargetFloor() > LiftConstants.MAXIMUM_FLOOR) {
      throw new InvalidPayloadException(
          "Target floor must be between 0 and " + LiftConstants.MAXIMUM_FLOOR);
    }
  }
}
