import { memo } from "react";

import { Floor } from "@/types";
import { FloorView } from "@/components";

export type BuildingProps = {
  floors: Floor[];
  onUpPressed: (floorNumber: number) => void;
  onDownPressed: (floorNumber: number) => void;
};

const Building = memo(
  ({ floors, onUpPressed, onDownPressed }: BuildingProps) => {
    return (
      <div className="flex flex-col gap-2">
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
