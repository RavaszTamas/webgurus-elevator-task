package com.tamas.ravasz.learning.lift.domain.panels;

import com.tamas.ravasz.learning.lift.constants.LiftConstants;
import com.tamas.ravasz.learning.lift.domain.buttons.InternalButton;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/** Button panel located inside a lift. */
@Getter
@Setter
public class InternalLiftPanel {
  private final List<InternalButton> internalButtons;

  public InternalLiftPanel() {
    this.internalButtons = new ArrayList<>();
    for (int i = 0; i <= LiftConstants.MAXIMUM_FLOOR; i++) {
      internalButtons.add(new InternalButton(i, false));
    }
  }

  public void pressButton(int floorNumber) {
    if (floorNumber < 0 || floorNumber > LiftConstants.MAXIMUM_FLOOR) {
      throw new IndexOutOfBoundsException();
    }
    internalButtons.get(floorNumber).pressButton();
  }

  public void clearButton(int floorNumber) {
    if (floorNumber < 0 || floorNumber > LiftConstants.MAXIMUM_FLOOR) {
      throw new IndexOutOfBoundsException();
    }
    internalButtons.get(floorNumber).releaseButton();
  }
}
