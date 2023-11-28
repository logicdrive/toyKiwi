// 특정 길이의 비디오를 일정 간격으로 이동하면서 재생시키는 샘플

import { Button } from '@mui/material';
import React, { useState, useEffect, useRef } from 'react';
import ReactPlayer from 'react-player'

function App() {
  const reactPlayerRef = useRef(null);
  const [reactPlayerProps, setReactPlayerProps] = useState({
    url: "/public/tempCuted.mp4",
    controls: true,
    playing: true
  });
  const [playerCustomProps, setPlayerCustomProps] = useState({
    currentTimeIndex: 0,
    timeRanges: [
      {startTimeSec: 0, endTimeSec: 3},
      {startTimeSec: 3, endTimeSec: 6},
      {startTimeSec: 6, endTimeSec: 9},
      {startTimeSec: 9, endTimeSec: 12},
      {startTimeSec: 12, endTimeSec: 15}
    ]
  })


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


  const prevButtonClicked = () => {
    if(playerCustomProps.currentTimeIndex <= 0) return
    setPlayerCustomProps((playerCustomProps) => {
      return {
        ...playerCustomProps,
        currentTimeIndex: playerCustomProps.currentTimeIndex-1
      }
    })
    setReactPlayerProps((reactPlayerProps) => {
      return {
        ...reactPlayerProps,
        playing: true,
        controls: false
      }
    })
  }

  const nextButtonClicked = () => {
    if(playerCustomProps.currentTimeIndex >= playerCustomProps.timeRanges.length-1) return
    setPlayerCustomProps((playerCustomProps) => {
      return {
        ...playerCustomProps,
        currentTimeIndex: playerCustomProps.currentTimeIndex+1
      }
    })
    setReactPlayerProps((reactPlayerProps) => {
      return {
        ...reactPlayerProps,
        playing: true,
        controls: false
      }
    })
  }

  useEffect(() => {
    reactPlayerRef.current.seekTo(playerCustomProps.timeRanges[playerCustomProps.currentTimeIndex].startTimeSec);
  }, [playerCustomProps.currentTimeIndex, playerCustomProps.timeRanges])


  return (
    <>
      <ReactPlayer 
        url={reactPlayerProps.url} playing={reactPlayerProps.playing} controls={reactPlayerProps.controls} 
        onPlay={onPlay} ref={reactPlayerRef}
      />
      <Button onClick={prevButtonClicked}>PREV</Button>
      <Button onClick={nextButtonClicked}>NEXT</Button>
    </>
  )
}

export default App;
