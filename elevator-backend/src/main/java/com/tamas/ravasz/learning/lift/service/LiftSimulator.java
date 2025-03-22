package com.tamas.ravasz.learning.lift.service;

import com.tamas.ravasz.learning.lift.converter.FloorConverter;
import com.tamas.ravasz.learning.lift.converter.LiftConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LiftSimulator {

  private final SimpMessagingTemplate messagingTemplate;
  private final LiftService liftService;
  private final FloorService floorService;
  private final LiftConverter liftConverter;
  private final FloorConverter floorConverter;

  public LiftSimulator(
      SimpMessagingTemplate messagingTemplate,
      LiftService liftService,
      FloorService floorService,
      LiftConverter liftConverter,
      FloorConverter floorConverter) {
    this.messagingTemplate = messagingTemplate;
    this.liftService = liftService;
    this.floorService = floorService;
    this.liftConverter = liftConverter;
    this.floorConverter = floorConverter;
  }

  @Scheduled(fixedRateString = "${state.machine.step.milliseconds}")
  public void simulateLifts() {
    // Simulate movements
    liftService.moveLifts();
    // Update floor indicators
    floorService.updateFloorIndicators(liftService.getLifts());
    // Send messages to the subscribers
    messagingTemplate.convertAndSend(
        "/topic/lifts", liftConverter.convertSource(liftService.getLifts()));
    messagingTemplate.convertAndSend(
        "/topic/floors", floorConverter.convertSource(floorService.getFloors()));
  }
}
