package com.tamas.ravasz.learning.lift.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class InternalButtonDto {
  private int targetFloor;
  private boolean pressed;
}
