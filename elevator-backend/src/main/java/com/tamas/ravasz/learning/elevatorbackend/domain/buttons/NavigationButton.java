package com.tamas.ravasz.learning.elevatorbackend.domain.buttons;

import com.tamas.ravasz.learning.elevatorbackend.domain.enums.Direction;
import lombok.*;

/**
 * Button that is located on a floor.
 * Used to call an elevator to that floor
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NavigationButton implements Button {
    private boolean isPressed;
    private Direction direction;

    @Override
    public void pressButton() {
        isPressed = !isPressed;
    }

    @Override
    public void releaseButton() {
        this.isPressed = false;
    }
}
