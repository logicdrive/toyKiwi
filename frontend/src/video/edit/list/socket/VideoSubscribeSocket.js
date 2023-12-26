import { useEffect, useState } from 'react';
import useWebSocket from "react-use-websocket"
import APIConfig from '../../../../APIConfig';

const VideoSubscribeSocket = (notifiedVideoStatus) => {
  const { sendJsonMessage, lastJsonMessage } = useWebSocket(
    `${APIConfig.collectedDataSocketUrl}/videoSubscribe`,
    {
      share: false,
      shouldReconnect: () => true,
    },
  )

  
  useEffect(() => {
      console.log(`[EFFECT] Notified video status by socket: <lastJsonMessage:${JSON.stringify(lastJsonMessage)}>`)
      if((lastJsonMessage !== null) && lastJsonMessage.videoId && lastJsonMessage.videoStatus)
        notifiedVideoStatus(lastJsonMessage.videoId, lastJsonMessage.videoStatus)
      else
        console.log(`[EFFECT] Ignored Data: ${lastJsonMessage}`)
  }, [lastJsonMessage, notifiedVideoStatus])


  const subscribeVideoStatus = useState(() => {
    return (videoId) => {
      console.log(`[EFFECT] Subscribe video by socket: <url:'${APIConfig.collectedDataSocketUrl}/videoSubscribe' videoId:${videoId}>`)
      sendJsonMessage({videoId: videoId})
    }
  })

  return subscribeVideoStatus
}

export default VideoSubscribeSocket;