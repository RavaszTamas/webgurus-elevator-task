package com.tamas.ravasz.learning.elevatorbackend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ElevatorSystemConfig {
    public int minimumFloor;
    public int maximumFloor;
}
