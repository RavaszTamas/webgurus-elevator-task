package com.tamas.ravasz.learning.lift.converter;

import com.tamas.ravasz.learning.lift.domain.lift.Lift;
import com.tamas.ravasz.learning.lift.dto.InternalButtonDto;
import com.tamas.ravasz.learning.lift.dto.InternalPanelDto;
import com.tamas.ravasz.learning.lift.dto.LiftDto;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class LiftConverter extends BaseConverter<Lift, LiftDto> {
  @Override
  public LiftDto convertSource(Lift lift, boolean expand) {
    return LiftDto.builder()
        .id(lift.getId())
        .state(lift.getState())
        .direction(lift.getDirection())
        .currentFloor(lift.getCurrentFloor())
        .internalPanel(
            InternalPanelDto.builder()
                .internalButtons(
                    lift.getInternalPanel().getInternalButtons().stream()
                        .map(
                            internalPanelButton ->
                                InternalButtonDto.builder()
                                    .pressed(internalPanelButton.isPressed())
                                    .targetFloor(internalPanelButton.getTargetFloor())
                                    .build())
                        .collect(Collectors.toList()))
                .build())
        .build();
  }

  @Override
  public Lift convertTarget(LiftDto liftDto, boolean expand) {
    return new Lift(liftDto.getId(), liftDto.getCurrentFloor());
  }
}
