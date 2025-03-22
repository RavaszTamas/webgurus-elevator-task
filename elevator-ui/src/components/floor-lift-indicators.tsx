import { memo } from "react";
import { Direction, LiftIndicators } from "@/types";
import { Triangle } from "lucide-react";

export type FloorLiftIndicatorProps = {
  liftIndicators: LiftIndicators;
};

/**
 * Shows the direction of the lifts on the floor
 */
const FloorLiftIndicators = memo(
  ({ liftIndicators }: FloorLiftIndicatorProps) => {
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
        {Object.keys(liftIndicators).map((liftId) => (
          <div key={liftId} className="flex flex-row items-center">
            <h4>Elevator: #{liftId}</h4>
            {getTriangleIndicator(liftIndicators[liftId])}
          </div>
        ))}
      </div>
    );
  },
);

FloorLiftIndicators.displayName = "FloorLiftIndicators";

export { FloorLiftIndicators };
