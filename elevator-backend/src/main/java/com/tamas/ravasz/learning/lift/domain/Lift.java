package com.tamas.ravasz.learning.lift.domain;

import com.tamas.ravasz.learning.lift.domain.enums.Direction;
import com.tamas.ravasz.learning.lift.domain.enums.LiftState;
import com.tamas.ravasz.learning.lift.domain.panels.InternalLiftPanel;
import com.tamas.ravasz.learning.lift.domain.requests.ExternalRequest;
import com.tamas.ravasz.learning.lift.domain.requests.InternalRequest;
import java.util.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/** The representation state for the lift It is a state machine */
@Getter
@Slf4j
public class Lift {

  private final int NO_TARGET_FLOOR = -1;

  private final String id;
  private final Queue<InternalRequest> internalRequests; // Internal stopping points
  private final Queue<ExternalRequest> requests; // Requests submitted to the elevator externally
  private final InternalLiftPanel internalPanel; // Internal button panel
  private int currentFloor; // Seven segment display, it can be a number
  private Direction direction; // the direction the elevator is going even when stopped or not
  private LiftState state;
  private int targetFloorToVisit = NO_TARGET_FLOOR; // The floor the elevator wants to go to

  public Lift(String id, int currentFloor) {
    this.id = id;
    this.currentFloor = currentFloor;
    this.direction = Direction.STOPPED;
    this.state = LiftState.STOPPED;
    internalRequests = new LinkedList<>();
    requests = new LinkedList<>();
    internalPanel = new InternalLiftPanel();
  }

  /**
   * Add an external request to the waiting queue
   *
   * @param externalRequest The new external request made inside the elevator
   */
  public void addExternalRequest(ExternalRequest externalRequest) {
    boolean exists = requests.stream().anyMatch(request -> request.equals(externalRequest));
    if (!exists) {
      requests.add(externalRequest);
    }
  }

  /**
   * Add an internal request to the waiting queue
   *
   * @param internalRequest The new internal request made inside the elevator
   */
  public void addInternalRequest(InternalRequest internalRequest) {
    boolean exists = internalRequests.stream().anyMatch(request -> request.equals(internalRequest));
    if (!exists) {
      internalRequests.add(internalRequest);
    }
  }

  /**
   * Clear a pressed button on the panel
   *
   * @param floorNumber
   */
  public void pressButton(int floorNumber) {
    internalPanel.pressButton(floorNumber);
  }

  /**
   * Clear a pressed button on the panel
   *
   * @param floorNumber
   */
  public void clearButton(int floorNumber) {
    internalPanel.clearButton(floorNumber);
  }

  /** Update the display in the internal panels */
  public void updateLiftDisplay() {
    if (state == LiftState.STOPPED) {
      this.internalPanel.clearButton(currentFloor);
    }
  }

  /**
   * SCAN algorithm to move the elevator Picks the target floor from the latest request from the
   * queue. Any request that arrives after is then stored there. The elevator starts moving to the
   * specific direction, each {move} call can have a maximum of one floor movements at a time.
   */
  public void move() {
    log.info(
        "Lift " + id + " state: " + state + " direction: " + direction + " at " + currentFloor);
    log.info("Number of external requests: " + requests.size());
    log.info("Number of internal requests: " + internalRequests.size());

    if (handleStoppedButProgressingState()) return;

    if (direction == Direction.STOPPED) {
      handleStoppedCase();
    } else {
      handleMovementCase();
    }

    // if arrived
    handleArrivalCase();
  }

  /**
   * If the lift is moving then check if the current floor we are on is in the waiting queue If yes
   * then stop the lift and let the passengers out If there is nothing here then continue moving the
   * elevator
   */
  private void handleMovementCase() {
    boolean isThereExternalRequestOnFloor = checkIfCurrentFloorIsInTheExternalQueue();
    boolean isThereInternalRequestOnFloor = checkIfCurrentFloorIsInInternalQueue();

    if (isThereExternalRequestOnFloor) {
      state = LiftState.STOPPED;
      requests.removeIf(request -> request.getTargetFloor() == currentFloor);
    }
    if (isThereInternalRequestOnFloor) {
      state = LiftState.STOPPED;
      internalRequests.removeIf(request -> request.getTargetFloor() == currentFloor);
    }
    if (!isThereInternalRequestOnFloor && !isThereExternalRequestOnFloor) {
      moveByDirection();
    }
  }

  /** If the lift has arrived clear up the direction and the state */
  private void handleArrivalCase() {
    if (currentFloor == targetFloorToVisit) {
      direction = Direction.STOPPED;
      state = LiftState.STOPPED;
      targetFloorToVisit = -1;
    }
  }

  /**
   * Check if the state machine has to move on or not It will check if there is a pending direction
   * where we the elevator is heading and if so the state machine will transition to the moving
   * state
   *
   * @return true if the state machine can move on false if not
   */
  private boolean handleStoppedButProgressingState() {
    if (direction != Direction.STOPPED && state == LiftState.STOPPED) {
      state = LiftState.MOVING;
      return true;
    }
    return false;
  }

  private void handleStoppedCase() {
    /**
     * If the elevator isn't moving in any direction then we will check if the elevator has a
     * request
     */
    if (!requests.isEmpty()) {
      ExternalRequest request = requests.poll();
      log.info(
          "Lift "
              + id
              + " has request "
              + request.getDirection()
              + " to "
              + request.getTargetFloor());
      // The direction will be then the direction of the first request
      this.direction = calculateDirectionFromFloor(request.getTargetFloor());
      this.targetFloorToVisit = request.getTargetFloor();
      this.state = LiftState.MOVING;
    }

    if (!internalRequests.isEmpty()) {
      InternalRequest request = internalRequests.poll();
      log.info("Lift " + id + " has request to " + request.getTargetFloor());

      this.direction = calculateDirectionFromFloor(request.getTargetFloor());
      this.targetFloorToVisit = request.getTargetFloor();
      this.state = LiftState.MOVING;
    }
  }

  private void moveByDirection() {

    state = LiftState.MOVING;

    if (Direction.UP == direction) {
      this.currentFloor++;
    } else if (Direction.DOWN == direction) {
      this.currentFloor--;
    }
  }

  /**
   * Determine which direction will be the lift going
   *
   * @param floor the floor the lift is heading to
   * @return the direction where the lift is heading
   */
  private Direction calculateDirectionFromFloor(int floor) {
    int difference = currentFloor - floor;
    if (difference < 0) return Direction.UP;
    else if (difference > 0) return Direction.DOWN;
    else return Direction.STOPPED;
  }

  private boolean checkIfCurrentFloorIsInTheExternalQueue() {
    log.info(
        "Check for elevator {} if current floor is in the requests Queue floor: {}",
        id,
        currentFloor);
    boolean result =
        requests.stream()
            .anyMatch(
                request -> {
                  return request.getDirection() == direction
                      && request.getTargetFloor() == currentFloor;
                });

    log.info("Result of check for " + result);

    return result;
  }

  private boolean checkIfCurrentFloorIsInInternalQueue() {
    log.info(
        "Check for elevator {} if current floor is in the internal requests Queue floor: {}",
        id,
        currentFloor);
    boolean result =
        internalRequests.stream().anyMatch(request -> request.getTargetFloor() == currentFloor);

    log.info("Result of check for " + result);

    return result;
  }
}
