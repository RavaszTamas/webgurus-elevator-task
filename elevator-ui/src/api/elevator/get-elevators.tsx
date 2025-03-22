import { queryOptions, useQuery } from "@tanstack/react-query";

import { api } from "@/lib/api-client.ts";
import { Elevator } from "@/types/elevator.ts";
import { QueryConfig } from "@/lib/react-query.ts";

export const getElevators = async (): Promise<Elevator[]> => {
  return api.get(`/elevator`);
};

export const getElevatorsQueryOptions = () => {
  return queryOptions({
    queryKey: ["elevator"],
    queryFn: getElevators,
  });
};

type UseElevatorsOptions = {
  queryConfig?: QueryConfig<typeof getElevatorsQueryOptions>;
};

export const useElevators = ({ queryConfig }: UseElevatorsOptions = {}) => {
  return useQuery({
    ...getElevatorsQueryOptions(),
    ...queryConfig,
  });
};
