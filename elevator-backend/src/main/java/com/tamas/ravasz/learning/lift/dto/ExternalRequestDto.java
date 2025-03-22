package com.tamas.ravasz.learning.lift.dto;

import com.tamas.ravasz.learning.lift.domain.enums.Direction;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExternalRequestDto {
  Direction direction;
  int targetFloor;
}
