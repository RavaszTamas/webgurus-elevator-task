package com.tamas.ravasz.learning.lift.controller;

import com.tamas.ravasz.learning.lift.converter.LiftConverter;
import com.tamas.ravasz.learning.lift.dto.ExternalRequestDto;
import com.tamas.ravasz.learning.lift.dto.InternalRequestDto;
import com.tamas.ravasz.learning.lift.dto.LiftDto;
import com.tamas.ravasz.learning.lift.service.LiftService;
import com.tamas.ravasz.learning.lift.service.LiftSystem;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class LiftController {
  private final LiftSystem liftSystem;

  private final LiftService liftService;
  private final LiftConverter liftConverter;

  @GetMapping("/lift")
  public List<LiftDto> getLifts() {
    return liftConverter.convertSource(liftService.getLifts());
  }

  @PostMapping("lift/call")
  public void callLift(@RequestBody ExternalRequestDto externalLiftCall) {
    liftSystem.callLiftToFloor(externalLiftCall);
  }

  @PostMapping("lift/request")
  public void requestLift(@RequestBody InternalRequestDto internalLiftCall) {
    liftSystem.callFromTheLiftPanel(internalLiftCall);
  }
}
