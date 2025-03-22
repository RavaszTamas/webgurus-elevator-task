import { z } from "zod";

export const elevatorState = z.enum(["MOVING", "STOPPED"]);
export const directionEnum = z.enum(["UP", "DOWN", "STOPPED"]);

export type ElevatorState = z.infer<typeof elevatorState>;
export type Direction = z.infer<typeof directionEnum>;

export const internalPanel = z.object({
  internalButtons: z.array(
    z.object({
      targetFloor: z.number(),
      pressed: z.boolean(),
    }),
  ),
});

export type InternalPanel = z.infer<typeof internalPanel>;

export const elevatorSchema = z.object({
  id: z.string(),
  state: elevatorState,
  direction: directionEnum,
  currentFloor: z.number(),
  targetFloorToVisit: z.number(),
  internalPanel: internalPanel,
});

export type Elevator = z.infer<typeof elevatorSchema>;

export const elevatorIndicators = z.record(z.string(), directionEnum);

export type ElevatorIndicators = z.infer<typeof elevatorIndicators>;

export const floorSchema = z.object({
  floorNumber: z.number(),
  elevatorDirectionIndicators: elevatorIndicators,
  floorButtonPanel: z.object({
    navigationButtons: z.record(
      directionEnum,
      z.object({
        pressed: z.boolean(),
        direction: directionEnum,
      }),
    ),
  }),
});

export type Floor = z.infer<typeof floorSchema>;

export type ElevatorSystemConfig = {
  minimumFloor: number;
  maximumFloor: number;
};

export type OnFloorButtonClicked = {
  elevatorId: string;
  floorNumber: number;
};
