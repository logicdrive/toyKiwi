// 특정 길이의 비디오를 일정 간격으로 이동하면서 재생시키는 샘플

import React, { useState, useEffect, useRef } from 'react';
import ReactPlayer from 'react-player'

function CuttedVideoPlayer(props) {
  const reactPlayerRef = useRef(null);
  const [reactPlayerProps, setReactPlayerProps] = useState({
    url: props.url,
    controls: true,
    playing: true
  });
  const [playerCustomProps, setPlayerCustomProps] = useState({
    currentTimeIndex: props.currentTimeIndex,
    initialCurrentTimeIndex: props.currentTimeIndex,
    timeRanges: props.timeRanges
  })


  // 처음 랜더링시에 작동되어서 컨트롤이 사라지도록 되는 현상을 방지하기 위해서
  const isCurrentTimeIndexChanged = useRef(false);
  useEffect(() => {
    if((!isCurrentTimeIndexChanged.current) && (playerCustomProps.initialCurrentTimeIndex === props.currentTimeIndex)) {
      return;
    }
    isCurrentTimeIndexChanged.current = true

    setPlayerCustomProps((playerCustomProps) => {
      return {
        ...playerCustomProps,
        currentTimeIndex: props.currentTimeIndex
      }
    })
    setReactPlayerProps((reactPlayerProps) => {
      return {
        ...reactPlayerProps,
        playing: true,
        controls: false
      }
    })
  }, [props.currentTimeIndex, playerCustomProps.initialCurrentTimeIndex])

  useEffect(() => {
    reactPlayerRef.current.seekTo(playerCustomProps.timeRanges[playerCustomProps.currentTimeIndex].startTimeSec);
  }, [playerCustomProps.currentTimeIndex, playerCustomProps.timeRanges])


  // 재생이후 일정시간이 지나서 멈추게 하고, 다시 클릭시에 이전 시작시간에서 시작되도록 만들기 위해서
  const onPlay = () => {
    const currentTimeRange = playerCustomProps.timeRanges[playerCustomProps.currentTimeIndex]
    reactPlayerRef.current.seekTo(currentTimeRange.startTimeSec);


    setReactPlayerProps((reactPlayerProps) => {
      return {
        ...reactPlayerProps,
        playing: true,
        controls: false
      }
    })

    setTimeout(() => {
      setReactPlayerProps((reactPlayerProps) => {
        return {
          ...reactPlayerProps,
          playing: false,
          controls: true
        }
      })
    }, (currentTimeRange.endTimeSec-currentTimeRange.startTimeSec)*1000)
  }


  return (
    <>
      <ReactPlayer 
        url={reactPlayerProps.url} playing={reactPlayerProps.playing} controls={reactPlayerProps.controls} 
        onPlay={onPlay} ref={reactPlayerRef} width="100%"
      />
    </>
  )
}

export default CuttedVideoPlayer;
