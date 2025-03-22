package com.tamas.ravasz.learning.lift.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LiftSystemConfig {
  public int minimumFloor;
  public int maximumFloor;
}
