import { z } from "zod";
import { api } from "@/lib/api-client.ts";
import { MutationConfig } from "@/lib/react-query.ts";
import { useMutation } from "@tanstack/react-query";

export const createExternalRequestSchemaInput = z.object({
  direction: z.enum(["UP", "DOWN"]),
  targetFloor: z.number(),
});

export type CreateExternalRequestInput = z.infer<
  typeof createExternalRequestSchemaInput
>;

const createExternalRequest = ({
  data,
}: {
  data: CreateExternalRequestInput;
}) => {
  return api.post("/elevator/call", data);
};

type UseCreateExternalRequestOptions = {
  mutationConfig?: MutationConfig<typeof createExternalRequest>;
};

export const useCreateExternalRequest = ({
  mutationConfig,
}: UseCreateExternalRequestOptions = {}) => {
  return useMutation({
    mutationFn: createExternalRequest,
    ...mutationConfig,
  });
};
