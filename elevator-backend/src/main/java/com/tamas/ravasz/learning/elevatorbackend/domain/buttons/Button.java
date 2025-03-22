package com.tamas.ravasz.learning.elevatorbackend.domain.buttons;

/**
 * This interface represents the base behaviours of the button.
 * The basic behaviour one can do with a button is to press it
 * The elevator system can also clear the button state, when it arrives
 */
public interface Button {
    void pressButton();
    void releaseButton();
}
