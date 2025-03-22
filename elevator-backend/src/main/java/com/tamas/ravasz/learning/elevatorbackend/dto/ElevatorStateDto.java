package com.tamas.ravasz.learning.elevatorbackend.dto;

import com.tamas.ravasz.learning.elevatorbackend.domain.enums.Direction;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ElevatorStateDto {
    public String ID;
    public Direction direction;
    public int currentFloor;

}
