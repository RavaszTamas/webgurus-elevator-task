package com.tamas.ravasz.learning.lift.converter;

import com.tamas.ravasz.learning.lift.domain.buttons.NavigationButton;
import com.tamas.ravasz.learning.lift.domain.enums.Direction;
import com.tamas.ravasz.learning.lift.domain.floor.Floor;
import com.tamas.ravasz.learning.lift.dto.FloorButtonPanelDto;
import com.tamas.ravasz.learning.lift.dto.FloorDto;
import com.tamas.ravasz.learning.lift.dto.NavigationButtonsDto;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class FloorConverter extends BaseConverter<Floor, FloorDto> {
  @Override
  public FloorDto convertSource(Floor floor, boolean expand) {

    Map<Direction, NavigationButtonsDto> navigationButtons = new HashMap<>();

    for (Map.Entry<Direction, NavigationButton> navigationButtonEntry :
        floor.getFloorButtonPanel().getNavigationButtons().entrySet()) {

      navigationButtons.put(
          navigationButtonEntry.getKey(),
          NavigationButtonsDto.builder()
              .direction(navigationButtonEntry.getValue().getDirection())
              .pressed(navigationButtonEntry.getValue().isPressed())
              .build());
    }

    return FloorDto.builder()
        .floorNumber(floor.getFloorNumber())
        .liftDirectionIndicators(floor.getLiftDirectionIndicators())
        .floorButtonPanel(
            FloorButtonPanelDto.builder().navigationButtons(navigationButtons).build())
        .build();
  }

  @Override
  public Floor convertTarget(FloorDto floorDto, boolean expand) {
    return null;
  }
}
