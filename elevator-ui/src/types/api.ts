import { z } from "zod";

export const liftState = z.enum(["MOVING", "STOPPED"]);
export const directionEnum = z.enum(["UP", "DOWN", "STOPPED"]);

export type LiftState = z.infer<typeof liftState>;
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

export const liftSchema = z.object({
  id: z.string(),
  state: liftState,
  direction: directionEnum,
  currentFloor: z.number(),
  internalPanel: internalPanel,
});

export type Lift = z.infer<typeof liftSchema>;

export const liftIndicators = z.record(z.string(), directionEnum);

export type LiftIndicators = z.infer<typeof liftIndicators>;

export const floorSchema = z.object({
  floorNumber: z.number(),
  liftDirectionIndicators: liftIndicators,
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
