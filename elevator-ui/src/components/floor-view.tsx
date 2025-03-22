import { memo } from "react";
import { Triangle } from "lucide-react";

import { Floor } from "@/types";
import { FloorLiftIndicators } from "@/components";

const FloorView = memo(
  (props: {
    floor: Floor;
    index: number;
    floors: Floor[];
    onClickUp: () => void;
    onClickDown: () => void;
  }) => {
    return (
      <div className="grid grid-cols-2 items-center border-white border p-2 rounded-md">
        <div>
          <h2>Floor {props.floor.floorNumber}</h2>
          <FloorLiftIndicators
            liftIndicators={props.floor.liftDirectionIndicators}
          />
        </div>
        <div className="flex flex-row gap-2 justify-center">
          {props.index < props.floors.length - 1 &&
            props.floor.floorButtonPanel.navigationButtons.UP && (
              <button
                onClick={props.onClickUp}
                className="data-[active=true]:text-red-500"
                data-active={
                  props.floor.floorButtonPanel.navigationButtons.UP.pressed
                }
              >
                <Triangle />
              </button>
            )}
          {props.index > 0 &&
            props.floor.floorButtonPanel.navigationButtons.DOWN && (
              <button
                onClick={props.onClickDown}
                className="data-[active=true]:text-red-500"
                data-active={
                  props.floor.floorButtonPanel.navigationButtons.DOWN.pressed
                }
              >
                <Triangle transform="rotate(180)" />
              </button>
            )}
        </div>
      </div>
    );
  },
);

FloorView.displayName = "FloorView";

export { FloorView };
