import { ElevatorSystemConfig } from "@/types/elevator";
import { useMemo } from "react";

type UseBuildingFloorRange = {
  config: ElevatorSystemConfig;
};

const useBuildingFloorRange = ({ config }: UseBuildingFloorRange) => {
  return useMemo(() => {
    const maxFloor = config.maximumFloor;

    return Array.from(
      { length: maxFloor + 1 },
      (_, floorNumber) => floorNumber,
    ).toReversed();
  }, [config]);
};

export { useBuildingFloorRange };
