package com.tamas.ravasz.learning.elevatorbackend.domain.panels;

import com.tamas.ravasz.learning.elevatorbackend.constants.ElevatorConstants;
import com.tamas.ravasz.learning.elevatorbackend.domain.buttons.InternalButton;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class InternalElevatorPanel {
    private final List<InternalButton> internalButtons;

    public InternalElevatorPanel() {
        this.internalButtons = new ArrayList<>();
        for(int i = 0; i <= ElevatorConstants.MAXIMUM_FLOOR; i++){
            internalButtons.add(new InternalButton(i, false));
        }
    }

    public void pressButton(int floorNumber) {
        if(floorNumber <  0 || floorNumber > ElevatorConstants.MAXIMUM_FLOOR){
            throw new IndexOutOfBoundsException();
        }
        internalButtons.get(floorNumber).pressButton();
    }

    public void clearButton(int floorNumber) {
        if(floorNumber <  0 || floorNumber > ElevatorConstants.MAXIMUM_FLOOR){
            throw new IndexOutOfBoundsException();
        }
        internalButtons.get(floorNumber).releaseButton();
    }

}
