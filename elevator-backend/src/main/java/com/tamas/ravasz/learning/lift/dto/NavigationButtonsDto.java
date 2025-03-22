package com.tamas.ravasz.learning.lift.dto;

import com.tamas.ravasz.learning.lift.domain.enums.Direction;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NavigationButtonsDto {
  boolean pressed;
  Direction direction;
}
