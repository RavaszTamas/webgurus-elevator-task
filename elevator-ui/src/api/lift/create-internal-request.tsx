import { z } from "zod";
import { useMutation } from "@tanstack/react-query";

import { api, MutationConfig } from "@/lib";

export const createInternalRequestSchemaInput = z.object({
  liftId: z.string(),
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
  return api.post("/lift/request", data);
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
