package com.tamas.ravasz.learning.elevatorbackend.dto;

import com.tamas.ravasz.learning.elevatorbackend.domain.enums.Direction;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExternalRequestDto {
    Direction direction;
    int targetFloor;
}
