import { StompSessionProvider } from "react-stomp-hooks";
import { LiftSystem } from "./layout";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { queryConfig } from "@/lib/react-query";
import { useState } from "react";

function App() {
  const [queryClient] = useState(
    () =>
      new QueryClient({
        defaultOptions: queryConfig,
      }),
  );

  return (
    <QueryClientProvider client={queryClient}>
      <StompSessionProvider url="http://localhost:8080/ws">
        <LiftSystem />
      </StompSessionProvider>
    </QueryClientProvider>
  );
}

export default App;
