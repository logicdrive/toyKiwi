// SelectQuiz 컴포넌트를 활용해보는 샘플

import React, { useState } from 'react';
import { Button } from '@mui/material';
import CuttedVideoPlayer from './CuttedVideoPlayer';

function App() {
  const [videoProps, setVideoProps] = useState({
    url: "/public/tempCuted.mp4",
    currentTimeIndex: 0,
    timeRanges: [
      {startTimeSec: 0, endTimeSec: 3},
      {startTimeSec: 3, endTimeSec: 6},
      {startTimeSec: 6, endTimeSec: 9},
      {startTimeSec: 9, endTimeSec: 12},
      {startTimeSec: 12, endTimeSec: 15}
    ]
  })

  
  const onClickPrevButton = () => {
    if(videoProps.currentTimeIndex === 0) {
      return
    }

    setVideoProps((videoProps) => {
      return {
        ...videoProps,
        currentTimeIndex: videoProps.currentTimeIndex-1
      }
    })
  }

  const onClickNextButton = () => {
    if(videoProps.currentTimeIndex === videoProps.timeRanges.length-1) {
      return
    }

    setVideoProps((videoProps) => {
      return {
        ...videoProps,
        currentTimeIndex: videoProps.currentTimeIndex+1
      }
    })
  }


  return (
    <>
      <CuttedVideoPlayer url={videoProps.url} currentTimeIndex={videoProps.currentTimeIndex} timeRanges={videoProps.timeRanges}/>
      <Button onClick={onClickPrevButton}>PREV</Button>
      <Button onClick={onClickNextButton}>NEXT</Button>
    </>
  )
}

export default App;