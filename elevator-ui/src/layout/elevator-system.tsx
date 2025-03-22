import { useCallback, useState } from "react";
import { useSubscription } from "react-stomp-hooks";
import { useElevatorSystemConfig } from "@/api";
import { Building } from "@/components/building.tsx";
import { ElevatorShaft } from "@/components/elevator-shaft.tsx";
import {
  Elevator,
  elevatorSchema,
  Floor,
  floorSchema,
  OnFloorButtonClicked,
} from "@/types/elevator.ts";
import { useCreateExternalRequest } from "@/api/elevator/post-external-request.tsx";
import { useCreateInternalRequest } from "@/api/elevator/create-internal-request.tsx";

const ElevatorSystem = () => {
  const config = useElevatorSystemConfig();
  const [elevators, setElevators] = useState<Elevator[]>([]);
  const [floors, setFloors] = useState<Floor[]>([]);

  const createExternalRequest = useCreateExternalRequest();
  const createInternalRequest = useCreateInternalRequest();

  useSubscription("/topic/elevators", (message) => {
    setElevators(elevatorSchema.array().parse(JSON.parse(message.body)));
  });

  useSubscription("/topic/floors", (message) => {
    console.log(message.body);

    setFloors(floorSchema.array().parse(JSON.parse(message.body)));
  });

  const onUpPressed = useCallback(
    (targetFloor: number) => {
      createExternalRequest.mutate({
        data: {
          direction: "UP",
          targetFloor: targetFloor,
        },
      });
    },
    [createExternalRequest],
  );

  const onDownPressed = useCallback(
    (targetFloor: number) => {
      createExternalRequest.mutate({
        data: {
          direction: "DOWN",
          targetFloor: targetFloor,
        },
      });
    },
    [createExternalRequest],
  );
  const onFloorButtonClicked = useCallback(
    (payload: OnFloorButtonClicked) => {
      createInternalRequest.mutate({
        data: {
          elevatorId: payload.elevatorId,
          targetFloor: payload.floorNumber,
        },
      });
    },
    [createInternalRequest],
  );

  if (!config.data) return null;

  return (
    <div className="flex flex-row justify-evenly items-center">
      <Building
        floors={floors}
        onUpPressed={onUpPressed}
        onDownPressed={onDownPressed}
      />

      <div className="flex flex-row gap-4">
        {elevators?.map((elevator) => (
          <ElevatorShaft
            key={elevator.id}
            elevator={elevator}
            config={config.data}
            onFloorButtonClicked={onFloorButtonClicked}
          />
        ))}
      </div>
    </div>
  );
};
export { ElevatorSystem };
