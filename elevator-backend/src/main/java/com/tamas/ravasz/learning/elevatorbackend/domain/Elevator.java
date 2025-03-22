package com.tamas.ravasz.learning.elevatorbackend.domain;

import com.tamas.ravasz.learning.elevatorbackend.domain.enums.Direction;
import com.tamas.ravasz.learning.elevatorbackend.domain.enums.ElevatorState;
import com.tamas.ravasz.learning.elevatorbackend.domain.panels.InternalElevatorPanel;
import com.tamas.ravasz.learning.elevatorbackend.domain.requests.ExternalRequest;
import com.tamas.ravasz.learning.elevatorbackend.domain.requests.InternalRequest;
import java.util.*;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/** The representation state for the elevator It is a state machine */
@Getter
@Setter
@Slf4j
public class Elevator {

  private String id;
  private int currentFloor; // Seven segment display, it can be a number
  private Direction direction; // the direction the elevator is going even when stopped or not
  private ElevatorState state;

  private Queue<InternalRequest> internalRequests; // Internal stopping points
  private Queue<ExternalRequest> requests; // Requests submitted to the elevator externally

  private int targetFloorToVisit = -1; // The floor the elevator wants to go to

  private InternalElevatorPanel internalPanel;


  public Elevator(String id, int currentFloor) {
    this.id = id;
    this.currentFloor = currentFloor;
    this.direction = Direction.STOPPED;
    internalRequests = new LinkedList<>();
    requests = new LinkedList<>();
    internalPanel = new InternalElevatorPanel();
  }

  /**
   * SCAN algorithm to move the elevator Picks the target floor from the latest request from the
   * queue. Any request that arrives after is then stored there. The elevator starts moving to the
   * specific direction, each {move} call can have a maximum of one floor movements at a time.
   */
  public void move() {
    log.info("Elevator " + id + " state: " + direction + " at " + currentFloor);
    log.info("Number of external requests: " + requests.size());
    log.info("Number of internal requests: " + internalRequests.size());

    if (direction == Direction.STOPPED) {
      handleStoppedCase();
    } else if (direction == Direction.DOWN) {
      moveByDirection();
    } else {
      moveByDirection();
    }

    // if arrived
    if (currentFloor == targetFloorToVisit) {
      direction = Direction.STOPPED;
      state = ElevatorState.STOPPED;
      targetFloorToVisit = -1;
    }
  }

  private void handleStoppedCase() {
    /**
     * If the elevator isn't moving in any direction then we will check if the elevator has a
     * request
     */
    if (!requests.isEmpty()) {
      ExternalRequest request = requests.poll();
      log.info(
          "Elevator "
              + id
              + " has request "
              + request.getDirection()
              + " to "
              + request.getTargetFloor());
      // The direction will be then the direction of the first request
      this.direction = calculateDirectionFromFloor(request.getTargetFloor());
      this.targetFloorToVisit = request.getTargetFloor();
      this.state = ElevatorState.MOVING;
    }
    if (!internalRequests.isEmpty()) {
      InternalRequest request = internalRequests.poll();
      log.info("Elevator " + id + " has request to " + request.getTargetFloor());

      this.direction = calculateDirectionFromFloor(request.getTargetFloor());
      this.targetFloorToVisit = request.getTargetFloor();
      this.state = ElevatorState.MOVING;
    }
  }

  private void moveByDirection() {

    if (state == ElevatorState.STOPPED) return;

    if (Direction.UP == direction) {
      this.currentFloor++;
    } else if (Direction.DOWN == direction) {
      this.currentFloor--;
    }
  }

  private Direction calculateDirectionFromFloor(int floor) {
    int difference = currentFloor - floor;
    if (difference < 0) return Direction.UP;
    else if (difference > 0) return Direction.DOWN;
    else return Direction.STOPPED;
  }

  private boolean checkIfCurrentFloorIsInTheQueue() {
    return requests.stream()
        .noneMatch(
            request ->
                request.getDirection() == direction && request.getTargetFloor() == currentFloor);
  }

  private TreeSet<Integer> mergeTargets() {

    // Add the internally submitted targets to the TreeSet

    TreeSet<Integer> targets =
        internalRequests.stream()
            .map(InternalRequest::getTargetFloor)
            .collect(Collectors.toCollection(TreeSet::new));

    // Add the external requests but only those that the direction corresponds with

    targets.addAll(
        requests.stream()
            .filter(request -> request.getDirection() == direction)
            .map(ExternalRequest::getTargetFloor)
            .collect(Collectors.toCollection(TreeSet::new)));

    return targets;
  }
}
