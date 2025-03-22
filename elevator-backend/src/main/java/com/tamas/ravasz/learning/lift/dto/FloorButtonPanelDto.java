package com.tamas.ravasz.learning.lift.dto;

import com.tamas.ravasz.learning.lift.domain.enums.Direction;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FloorButtonPanelDto {
  Map<Direction, NavigationButtonsDto> navigationButtons;
}
