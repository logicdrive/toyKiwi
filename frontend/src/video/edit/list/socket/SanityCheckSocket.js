import { useEffect } from 'react';
import useWebSocket, { ReadyState } from "react-use-websocket"
import APIConfig from '../../../../APIConfig';

const SanityCheckSocket = () => {
  const { sendJsonMessage, lastJsonMessage, readyState } = useWebSocket(
    `${APIConfig.collectedDataSocketUrl}/sanityCheck`,
    {
      share: false,
      shouldReconnect: () => true,
    },
  )


  useEffect(() => {
    if (readyState === ReadyState.OPEN) {
        const sendMessage = {"message": "Hello, World !"}
        console.log(`[EFFECT] Send message for sanityCheck by using socket: <url:'${APIConfig.collectedDataSocketUrl}/sanityCheck' sendMessage:${JSON.stringify(sendMessage)}>`)
        sendJsonMessage(sendMessage)
    }
  }, [readyState, sendJsonMessage])

  useEffect(() => {
    console.log(`[EFFECT] Got message from sanityCheck socket server: <lastJsonMessage:${JSON.stringify(lastJsonMessage)}>`)
  }, [lastJsonMessage])
}

export default SanityCheckSocket;