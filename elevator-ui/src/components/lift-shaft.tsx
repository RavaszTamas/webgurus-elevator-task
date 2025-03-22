import { memo } from "react";

import { Display } from "react-7-segment-display";

import { Lift, OnFloorButtonClicked } from "@/types";

export type LiftProps = {
  lift: Lift;
  onFloorButtonClicked: ({ liftId, floorNumber }: OnFloorButtonClicked) => void;
};

const LiftShaft = memo(({ lift, onFloorButtonClicked }: LiftProps) => {
  return (
    <div className="flex flex-col gap-2 border-white border p-2">
      <div className="flex flex-col items-center">
        <h4>Elevator Shaft #{lift.id}</h4>
        <h3>State: #{lift.state}</h3>
        <h3>Direction: #{lift.direction}</h3>
      </div>
      <section className="flex flex-col items-center justify-center  gap-2 relative">
        <div className="bg-black rounded-md">
          <Display
            value={lift.currentFloor}
            count={1}
            height={96}
            color={"red"}
            skew={false}
          />
        </div>
      </section>
      <div className="grid grid-cols-3 gap-2 border-white border rounded-md bg-gray-500 p-2">
        {lift.internalPanel.internalButtons
          .toReversed()
          .map((internalButton) => (
            <button
              key={internalButton.targetFloor}
              onClick={() =>
                onFloorButtonClicked({
                  liftId: lift.id,
                  floorNumber: internalButton.targetFloor,
                })
              }
              className="data-[active=true]:text-red-500 rounded-xl"
              data-active={internalButton.pressed}
            >
              {internalButton.targetFloor}
            </button>
          ))}
      </div>
    </div>
  );
});

LiftShaft.displayName = "LiftShaft";

export { LiftShaft };
