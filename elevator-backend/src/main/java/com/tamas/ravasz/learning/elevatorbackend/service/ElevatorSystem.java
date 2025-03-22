package com.tamas.ravasz.learning.elevatorbackend.service;

import com.tamas.ravasz.learning.elevatorbackend.constants.ElevatorConstants;
import com.tamas.ravasz.learning.elevatorbackend.domain.*;
import com.tamas.ravasz.learning.elevatorbackend.domain.floor.Floor;
import com.tamas.ravasz.learning.elevatorbackend.domain.requests.ExternalRequest;
import com.tamas.ravasz.learning.elevatorbackend.domain.requests.InternalRequest;
import com.tamas.ravasz.learning.elevatorbackend.dto.ElevatorStateDto;
import com.tamas.ravasz.learning.elevatorbackend.dto.ElevatorSystemConfig;
import com.tamas.ravasz.learning.elevatorbackend.dto.ExternalRequestDto;
import com.tamas.ravasz.learning.elevatorbackend.dto.InternalRequestDto;
import com.tamas.ravasz.learning.elevatorbackend.exception.ElevatorNotFoundException;
import com.tamas.ravasz.learning.elevatorbackend.exception.FloorNotFoundException;
import com.tamas.ravasz.learning.elevatorbackend.validator.ElevatorValidator;
import java.util.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ElevatorSystem {

  private final Map<String, Elevator> elevators;

  private final SimpMessagingTemplate messagingTemplate;
  private final ElevatorValidator elevatorValidator;

  @Getter private final ElevatorSystemConfig elevatorSystemConfig;

  private final HashMap<Integer, Floor> floors;

  public ElevatorSystem(
      SimpMessagingTemplate messagingTemplate, ElevatorValidator elevatorValidator) {
    this.messagingTemplate = messagingTemplate;
    this.elevatorValidator = elevatorValidator;

    elevators = new HashMap<>();
    elevators.put("A", new Elevator("A", ElevatorConstants.MINIMUM_FLOOR));
    elevators.put("B", new Elevator("B", ElevatorConstants.MAXIMUM_FLOOR));

    floors = new HashMap<>();

    for (int i = 0; i <= ElevatorConstants.MAXIMUM_FLOOR; i++) {
      floors.put(i, new Floor(i, elevators));
    }

    this.elevatorSystemConfig =
        ElevatorSystemConfig.builder()
            .minimumFloor(ElevatorConstants.MINIMUM_FLOOR)
            .maximumFloor(ElevatorConstants.MAXIMUM_FLOOR)
            .build();
  }

  public List<ElevatorStateDto> getElevators() {
    return elevators.values().stream()
        .map(
            elevator ->
                ElevatorStateDto.builder()
                    .ID(elevator.getId())
                    .currentFloor(elevator.getCurrentFloor())
                    .currentFloor(elevator.getCurrentFloor())
                    .direction(elevator.getDirection())
                    .build())
        .toList();
  }

  public void callElevatorToFloor(ExternalRequestDto elevatorCall) {
    elevatorValidator.validateExternalRequest(elevatorCall);

    try {
      Elevator elevator = findBestElevator(elevatorCall);

      Floor floor = findFloorById(elevatorCall.getTargetFloor());

      floor.callDirection(elevatorCall.getDirection());

      elevator
          .getRequests()
          .add(
              ExternalRequest.builder()
                  .targetFloor(elevatorCall.getTargetFloor())
                  .direction(elevatorCall.getDirection())
                  .build());
    } catch (ElevatorNotFoundException e) {
      log.error(e.getMessage());
    }
  }

  public void callFromTheElevatorPanel(InternalRequestDto elevatorCall) {
    log.info("Incoming request: {}", elevatorCall.getElevatorId());
    elevatorValidator.validateInternalRequest(elevatorCall);
    try {
      Elevator elevator = findElevatorById(elevatorCall.getElevatorId());

      elevator.getInternalPanel().pressButton(elevatorCall.getTargetFloor());

      log.info("Found elevator: {}", elevator.getId());
      elevator.getInternalPanel().pressButton(elevatorCall.getTargetFloor());
      elevator
          .getInternalRequests()
          .add(InternalRequest.builder().targetFloor(elevatorCall.getTargetFloor()).build());
    } catch (ElevatorNotFoundException e) {
      log.error(e.getMessage());
    }
  }

  private Elevator findBestElevator(ExternalRequestDto elevatorCall)
      throws ElevatorNotFoundException {
    log.info(
        "Finding best elevator for floor by searching for the closest"
            + elevatorCall.getTargetFloor());
    Elevator closestElevator = null;

    int minDistance = Integer.MAX_VALUE;
    for (Elevator elevator : elevators.values()) {

      int distance = Math.abs(elevatorCall.getTargetFloor() - elevator.getCurrentFloor());

      if (distance < minDistance) {
        closestElevator = elevator;
        minDistance = distance;
      }
    }
    if (closestElevator == null) {
      throw new ElevatorNotFoundException("Elevator not found");
    }

    log.info("Found best elevator for " + closestElevator.getId());
    return closestElevator;
  }

  private Elevator findElevatorById(String id) throws ElevatorNotFoundException {
    return Optional.ofNullable(elevators.get(id))
        .orElseThrow(() -> new ElevatorNotFoundException("Elevator with id " + id + " not found"));
  }

  private Floor findFloorById(Integer id) throws FloorNotFoundException {
    return Optional.ofNullable(floors.get(id))
        .orElseThrow(() -> new FloorNotFoundException("Floor with id " + id + " not found"));
  }

  @Scheduled(fixedRate = 1000)
  public void simulateElevators() {

    // move the elevators one floor given the strategy

    for (Elevator elevator : elevators.values()) {
      elevator.move();
    }

    //

    // update the floor displays

    for (Floor floor : floors.values()) {
      floor.updateIndicators(elevators);
    }

    messagingTemplate.convertAndSend("/topic/elevators", elevators.values());
    messagingTemplate.convertAndSend("/topic/floors", floors.values());
  }
}
