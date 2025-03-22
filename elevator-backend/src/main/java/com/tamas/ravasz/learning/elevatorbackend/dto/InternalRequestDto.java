package com.tamas.ravasz.learning.elevatorbackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InternalRequestDto {
    String elevatorId;
    int targetFloor;
}
