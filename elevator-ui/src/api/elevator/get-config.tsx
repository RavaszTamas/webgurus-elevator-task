import { api } from "@/lib/api-client.ts";
import { ElevatorSystemConfig } from "@/types/elevator.ts";
import { queryOptions, useQuery } from "@tanstack/react-query";
import { QueryConfig } from "@/lib/react-query.ts";

export const getElevatorSystemConfig =
  async (): Promise<ElevatorSystemConfig> => {
    return api.get(`/elevator/config`);
  };

export const getElevatorSystemConfigQueryOptions = () => {
  return queryOptions({
    queryKey: ["elevator-system-config"],
    queryFn: getElevatorSystemConfig,
  });
};

type UseElevatorsOptions = {
  queryConfig?: QueryConfig<typeof getElevatorSystemConfigQueryOptions>;
};

export const useElevatorSystemConfig = ({
  queryConfig,
}: UseElevatorsOptions = {}) => {
  return useQuery({
    ...getElevatorSystemConfigQueryOptions(),
    ...queryConfig,
  });
};
