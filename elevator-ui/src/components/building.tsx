import { Direction, ElevatorIndicators, Floor } from "@/types/elevator.ts";
import { memo } from "react";
import { Triangle } from "lucide-react";

export type BuildingProps = {
  floors: Floor[];
  onUpPressed: (floorNumber: number) => void;
  onDownPressed: (floorNumber: number) => void;
};

export type FloorElevatorIndicators = {
  elevatorIndicators: ElevatorIndicators;
};

const FloorElevatorIndicators = memo(
  ({ elevatorIndicators }: FloorElevatorIndicators) => {
    const getTriangleIndicator = (indicatorDirection: Direction) => {
      if (indicatorDirection === "UP") {
        return (
          <Triangle size={16} className="fill-green-700 stroke-green-700" />
        );
      }
      if (indicatorDirection === "DOWN") {
        return (
          <Triangle
            size={16}
            transform="rotate(180)"
            className="fill-green-700 stroke-green-700"
          />
        );
      }
      return null;
    };

    return (
      <div>
        {Object.keys(elevatorIndicators).map((elevatorId) => (
          <div key={elevatorId} className="flex flex-row items-center">
            <h4>Elevator: #{elevatorId}</h4>
            {getTriangleIndicator(elevatorIndicators[elevatorId])}
          </div>
        ))}
      </div>
    );
  },
);

FloorElevatorIndicators.displayName = "FloorElevatorIndicators";

function FloorView(props: {
  floor: Floor;
  index: number;
  floors: Floor[];
  onClickUp: () => void;
  onClickDown: () => void;
}) {
  return (
    <div className="grid grid-cols-2 items-center border-white border p-2 rounded-md">
      <div>
        <h2>Floor {props.floor.floorNumber}</h2>
        <FloorElevatorIndicators
          elevatorIndicators={props.floor.elevatorDirectionIndicators}
        />
      </div>
      <div className="flex flex-row gap-2 justify-center">
        {props.index < props.floors.length - 1 &&
          props.floor.floorButtonPanel.navigationButtons.UP && (
            <button
              onClick={props.onClickUp}
              disabled={
                props.floor.floorButtonPanel.navigationButtons.UP.pressed ===
                true
              }
            >
              {`${props.floor.floorButtonPanel.navigationButtons.UP.pressed}`}
              <Triangle transform="rotate(180)" />
            </button>
          )}
        {props.index > 0 &&
          props.floor.floorButtonPanel.navigationButtons.DOWN && (
            <button
              disabled={
                props.floor.floorButtonPanel.navigationButtons.DOWN.pressed ===
                true
              }
              onClick={props.onClickDown}
            >
              {`${props.floor.floorButtonPanel.navigationButtons.DOWN.pressed}`}
              <Triangle />
            </button>
          )}
      </div>
    </div>
  );
}

const Building = memo(
  ({ floors, onUpPressed, onDownPressed }: BuildingProps) => {
    return (
      <div className="flex flex-col gap-2">
        <h1>Building</h1>
        <div className="flex flex-col-reverse gap-4">
          {floors.map((floor, index) => (
            <FloorView
              key={floor.floorNumber}
              floor={floor}
              index={index}
              floors={floors}
              onClickUp={() => onUpPressed(floor.floorNumber)}
              onClickDown={() => onDownPressed(floor.floorNumber)}
            />
          ))}
        </div>
      </div>
    );
  },
);

Building.displayName = "Building";

export { Building };
