import { z } from "zod";
import { api } from "@/lib/api-client.ts";
import { MutationConfig } from "@/lib/react-query.ts";
import { useMutation } from "@tanstack/react-query";

export const createInternalRequestSchemaInput = z.object({
  elevatorId: z.string(),
  targetFloor: z.number(),
});

export type CreateInternalRequestInput = z.infer<
  typeof createInternalRequestSchemaInput
>;

const createInternalRequest = ({
  data,
}: {
  data: CreateInternalRequestInput;
}) => {
  return api.post("/elevator/request", data);
};

type UseCreateExternalRequestOptions = {
  mutationConfig?: MutationConfig<typeof createInternalRequest>;
};

export const useCreateInternalRequest = ({
  mutationConfig,
}: UseCreateExternalRequestOptions = {}) => {
  return useMutation({
    mutationFn: createInternalRequest,
    ...mutationConfig,
  });
};
