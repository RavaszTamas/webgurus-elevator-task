package com.tamas.ravasz.learning.elevatorbackend.controller;

import com.tamas.ravasz.learning.elevatorbackend.domain.Greeting;
import com.tamas.ravasz.learning.elevatorbackend.dto.ExternalRequestDto;
import com.tamas.ravasz.learning.elevatorbackend.dto.InternalRequestDto;
import com.tamas.ravasz.learning.elevatorbackend.dto.ElevatorStateDto;
import com.tamas.ravasz.learning.elevatorbackend.dto.ElevatorSystemConfig;
import com.tamas.ravasz.learning.elevatorbackend.service.ElevatorSystem;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class ElevatorController {

  private final ElevatorSystem elevatorSystem;

  @GetMapping("/elevator/config")
  public ElevatorSystemConfig getElevatorSystemConfig() {
    return elevatorSystem.getElevatorSystemConfig();
  }

  @GetMapping("/elevator")
  public List<ElevatorStateDto> getElevators() {
    return elevatorSystem.getElevators();
  }

  @PostMapping("elevator/call")
  public void callElevator(@RequestBody ExternalRequestDto elevatorCall) {
    elevatorSystem.callElevatorToFloor(elevatorCall);
  }

  @PostMapping("elevator/request")
  public void requestElevator(@RequestBody InternalRequestDto elevatorRequest) {
    elevatorSystem.callFromTheElevatorPanel(elevatorRequest);
  }

  @MessageMapping("/broadcast")
  @SendTo("/topic/reply")
  public Greeting broadcastMessage(@Payload String message) {
    return new Greeting(message);
  }
}
