package com.tamas.ravasz.learning.lift.dto;

import com.tamas.ravasz.learning.lift.domain.enums.Direction;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class FloorDto {
  int floorNumber;
  Map<String, Direction> liftDirectionIndicators;
  FloorButtonPanelDto floorButtonPanel;
}
