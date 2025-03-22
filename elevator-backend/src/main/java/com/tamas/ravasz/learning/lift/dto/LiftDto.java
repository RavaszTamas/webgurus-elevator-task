package com.tamas.ravasz.learning.lift.dto;

import com.tamas.ravasz.learning.lift.domain.enums.Direction;
import com.tamas.ravasz.learning.lift.domain.enums.LiftState;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class LiftDto {
  public String id;
  public Direction direction;
  public LiftState state;
  public int currentFloor;
  InternalPanelDto internalPanel;
}
