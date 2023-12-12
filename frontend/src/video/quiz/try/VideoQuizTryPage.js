import React, { useState, useContext, useEffect } from 'react';
import axios from 'axios';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { Card } from '@mui/material';
import Stack from '@mui/material/Stack';
import APIConfig from '../../../APIConfig';
import { AlertPopupContext } from '../../../_global/alertPopUp/AlertPopUpContext'
import CuttedVideoPlayer from './videoPlayer/CuttedVideoPlayer';
import VideoPlayerControllerCard from './videoPlayer/VideoPlayerControllerCard';
import VideoQuizCard from './videoQuiz/VideoQuizCard';
import VideoQuizTryAppBar from './VideoQuizTryAppBar';

// 예시 URL: http://localhost:3000/video/quiz/try?videoId=1
const VideoQuizTryPage = () => {
    const {addAlertPopUp} = useContext(AlertPopupContext);
    const [queryParameters] = useSearchParams()
    const navigate = useNavigate();


    const [uploadVideoInfo, setUploadVideoInfo] = useState({});
    const [subtitleInfos, setSubtitleInfos] = useState([])
    const [videoPlayerProps, setVideoPlayerProps] = useState({})
    useEffect(() => {
        (async () => {
            try {

                const response = await axios.get(`${APIConfig.collectedDataUrl}/videos/${queryParameters.get("videoId")}`);
                setUploadVideoInfo({
                    videoId: response.data.videoId,
                    uploadedUrl: response.data.uploadedUrl
                })

            } catch (error) {
                addAlertPopUp("업로된 동영상 정보를 가져오는 과정에서 오류가 발생했습니다!", "error");
                console.error("업로된 동영상 정보를 가져오는 과정에서 오류가 발생했습니다!", error);
            }

            try {

                const response = await axios.get(`${APIConfig.collectedDataUrl}/videos/${queryParameters.get("videoId")}/subtitles`);
                setSubtitleInfos(response.data.subtitles.map((subtitle) => {
                    return {
                        subtitleId: subtitle.subtitleId,
                        subtitle: subtitle.subtitle,
                        translatedSubtitle: subtitle.translatedSubtitle,
                        startSecond: subtitle.startSecond,
                        endSecond: subtitle.endSecond
                    }
                }))

            } catch (error) {
                addAlertPopUp("업로된 동영상 자막 정보를 가져오는 과정에서 오류가 발생했습니다!", "error");
                console.error("업로된 동영상 자막 정보를 가져오는 과정에서 오류가 발생했습니다!", error);
            }
        })()
    }, [addAlertPopUp, queryParameters])
    useEffect(() => {
        setVideoPlayerProps({
            url: uploadVideoInfo.uploadedUrl,
            currentTimeIndex: 0,
            limitedTimeIndex: 0,
            timeRanges: subtitleInfos.map((subtitleInfo) => {
                return {
                    startTimeSec: subtitleInfo.startSecond,
                    endTimeSec: subtitleInfo.endSecond
                }
            })
        })
    }, [uploadVideoInfo, subtitleInfos])
    

    const [quizInfo, setQuizInfo] = useState()
    useEffect(() => {
        if(!(subtitleInfos && subtitleInfos.length > 0)) return

        setQuizInfo({
            words: subtitleInfos[videoPlayerProps.currentTimeIndex].subtitle.split(" "),
            translatedSubtitle: subtitleInfos[videoPlayerProps.currentTimeIndex].translatedSubtitle
        })
        
    }, [subtitleInfos, videoPlayerProps.currentTimeIndex])


    const [quizResultInfo, setQuizResultInfo] = useState({
        correctedWordCount: 0,
        inCorrectedWordCount: 0
    })
    const onAllCorrect = (currentCorrectedWordCount, currentInCorrectedWordCount) => {
        setVideoPlayerProps((videoPlayerProps) => {
            return {
              ...videoPlayerProps,
              limitedTimeIndex: videoPlayerProps.limitedTimeIndex+1
            }
        })
        
        setQuizResultInfo((quizResultInfo) => {
            return {
                correctedWordCount: quizResultInfo.correctedWordCount + currentCorrectedWordCount,
                inCorrectedWordCount: quizResultInfo.inCorrectedWordCount + currentInCorrectedWordCount
            }
        })
        console.log("CURRENT RESULT: ", quizResultInfo)
    }


    const onClickPrevButton = () => {
        if(videoPlayerProps.currentTimeIndex === 0) {
          return
        }
    
        setVideoPlayerProps((videoPlayerProps) => {
          return {
            ...videoPlayerProps,
            currentTimeIndex: videoPlayerProps.currentTimeIndex-1
          }
        })
      }
    const onClickNextButton = () => {
        if(videoPlayerProps.currentTimeIndex >= videoPlayerProps.limitedTimeIndex) return
        if(videoPlayerProps.currentTimeIndex === videoPlayerProps.timeRanges.length-1) {
            navigate(`/video/quiz/result?videoId=${queryParameters.get("videoId")}&correctedWordCount=${quizResultInfo.correctedWordCount}&inCorrectedWordCount=${quizResultInfo.inCorrectedWordCount}`)
            return
        }
    
        setVideoPlayerProps((videoPlayerProps) => {
            return {
            ...videoPlayerProps,
            currentTimeIndex: videoPlayerProps.currentTimeIndex+1
            }
        })
    }


    return (
        <>
        <VideoQuizTryAppBar/>

        {
            (() => {
                if (videoPlayerProps.url && videoPlayerProps.timeRanges && 
                   (videoPlayerProps.timeRanges.length > 0) && quizInfo) {
                    return (
                        <>
                        <Stack spacing={0.5} sx={{marginTop: 1}}>
                            <Card variant="outlined">
                                <CuttedVideoPlayer url={videoPlayerProps.url} currentTimeIndex={videoPlayerProps.currentTimeIndex} timeRanges={videoPlayerProps.timeRanges}/>
                            </Card>
                            <VideoPlayerControllerCard videoPlayerProps={videoPlayerProps} 
                                        onClickPrevButton={onClickPrevButton} onClickNextButton={onClickNextButton}/>

                            <VideoQuizCard videoPlayerProps={videoPlayerProps} subtitleInfos={subtitleInfos}
                                           quizInfo={quizInfo} onAllCorrect={onAllCorrect}/>
                        </Stack>
                        </>
                    )
                }
            })()
        }
        </>
    );
}

export default VideoQuizTryPage;