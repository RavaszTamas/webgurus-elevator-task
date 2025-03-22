package com.tamas.ravasz.learning.elevatorbackend.domain.requests;

import com.tamas.ravasz.learning.elevatorbackend.domain.enums.Direction;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ExternalRequest {
    private int targetFloor;
    private Direction direction;
}
