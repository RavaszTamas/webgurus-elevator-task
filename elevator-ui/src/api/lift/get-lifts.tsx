import { queryOptions, useQuery } from "@tanstack/react-query";

import { api, QueryConfig } from "@/lib";
import { Lift } from "@/types";

export const getLifts = async (): Promise<Lift[]> => {
  return api.get(`/lift`);
};

export const getLiftsQueryOptions = () => {
  return queryOptions({
    queryKey: ["lift"],
    queryFn: getLifts,
  });
};

type UseLiftsOptions = {
  queryConfig?: QueryConfig<typeof getLiftsQueryOptions>;
};

export const useLifts = ({ queryConfig }: UseLiftsOptions = {}) => {
  return useQuery({
    ...getLiftsQueryOptions(),
    ...queryConfig,
  });
};
