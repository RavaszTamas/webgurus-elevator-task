import { useStompClient } from "react-stomp-hooks";

const PublishComponent = () => {
  const stompClient = useStompClient();

  const publish = () => {
    if (stompClient) {
      stompClient.publish({
        destination: "/app/broadcast",
        body: "Hello World",
      });
    }
  };

  return <button onClick={publish}>Send message</button>;
};

export { PublishComponent };
