package com.tamas.ravasz.learning.elevatorbackend.domain.requests;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Builder
public class InternalRequest {
    private int targetFloor;
}
