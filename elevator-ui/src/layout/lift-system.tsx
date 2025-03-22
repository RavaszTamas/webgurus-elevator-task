import { useCallback, useState } from "react";

import { useSubscription } from "react-stomp-hooks";

import { Building, LiftShaft } from "@/components";
import {
  Lift,
  liftSchema,
  Floor,
  floorSchema,
  OnFloorButtonClicked,
} from "@/types";
import { useCreateExternalRequest, useCreateInternalRequest } from "@/api";

const LiftSystem = () => {
  const [elevators, setElevators] = useState<Lift[]>([]);
  const [floors, setFloors] = useState<Floor[]>([]);

  const createExternalRequest = useCreateExternalRequest();
  const createInternalRequest = useCreateInternalRequest();

  useSubscription("/topic/lifts", (message) => {
    setElevators(liftSchema.array().parse(JSON.parse(message.body)));
  });

  useSubscription("/topic/floors", (message) => {
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
          liftId: payload.liftId,
          targetFloor: payload.floorNumber,
        },
      });
    },
    [createInternalRequest],
  );

  return (
    <div className="flex flex-row justify-evenly items-center">
      <Building
        floors={floors}
        onUpPressed={onUpPressed}
        onDownPressed={onDownPressed}
      />

      <div className="flex flex-row gap-4">
        {elevators?.map((elevator) => (
          <LiftShaft
            key={elevator.id}
            lift={elevator}
            onFloorButtonClicked={onFloorButtonClicked}
          />
        ))}
      </div>
    </div>
  );
};
export { LiftSystem };
