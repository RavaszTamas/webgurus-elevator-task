import { Display } from "react-7-segment-display";

import {
  Elevator,
  ElevatorSystemConfig,
  OnFloorButtonClicked,
} from "@/types/elevator";
import { memo } from "react";

export type ElevatorProps = {
  config: ElevatorSystemConfig;
  elevator: Elevator;
  onFloorButtonClicked: ({
    elevatorId,
    floorNumber,
  }: OnFloorButtonClicked) => void;
};

const ElevatorShaft = memo(
  ({ elevator, onFloorButtonClicked }: ElevatorProps) => {
    return (
      <div className="flex flex-col gap-2">
        <h4>Elevator Shaft #{elevator.id}</h4>
        <h4>Floor #{elevator.currentFloor}</h4>
        <h3>State: #{elevator.state}</h3>
        <h3>Direction: #{elevator.direction}</h3>
        <section className="flex flex-col gap-2 relative">
          <Display
            value={elevator.currentFloor}
            count={1}
            height={96}
            color={"red"}
            skew={false}
          />
        </section>
        <div className="grid grid-cols-3 gap-2">
          {elevator.internalPanel.internalButtons
            .toReversed()
            .map((internalButton) => (
              <button
                key={internalButton.targetFloor}
                onClick={() =>
                  onFloorButtonClicked({
                    elevatorId: elevator.id,
                    floorNumber: internalButton.targetFloor,
                  })
                }
              >
                {internalButton.targetFloor}
              </button>
            ))}
        </div>
      </div>
    );
  },
);

ElevatorShaft.displayName = "ElevatorShaft";

export { ElevatorShaft };
