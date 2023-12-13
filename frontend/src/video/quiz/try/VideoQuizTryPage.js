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
import QnACard from './qna/QnACard';
import QnADialog from './qna/QnADialog';

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

                console.log(`[EFFECT] Try to download video infos: <url:${`${APIConfig.collectedDataUrl}/videos/${queryParameters.get("videoId")}`}>`)
                const response = await axios.get(`${APIConfig.collectedDataUrl}/videos/${queryParameters.get("videoId")}`);
                setUploadVideoInfo({
                    videoId: response.data.videoId,
                    uploadedUrl: response.data.uploadedUrl
                })
                console.log("[EFFECT] Downloaded videos:", response)

            } catch (error) {
                addAlertPopUp("업로된 동영상 정보를 가져오는 과정에서 오류가 발생했습니다!", "error");
                console.error("업로된 동영상 정보를 가져오는 과정에서 오류가 발생했습니다!", error);
            }

            try {

                console.log(`[EFFECT] Try to download subtitle infos: <url:${`${APIConfig.collectedDataUrl}/videos/${queryParameters.get("videoId")}/subtitles`}>`)
                const response = await axios.get(`${APIConfig.collectedDataUrl}/videos/${queryParameters.get("videoId")}/subtitles`);
                setSubtitleInfos(response.data.subtitles.map((subtitle) => {
                    return {
                        subtitleId: subtitle.subtitleId,
                        subtitle: subtitle.subtitle,
                        translatedSubtitle: subtitle.translatedSubtitle,
                        startSecond: subtitle.startSecond,
                        endSecond: subtitle.endSecond,
                        question: subtitle.question,
                        answer: subtitle.answer
                    }
                }))
                console.log("[EFFECT] Downloaded subtitles:", response)

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

        setChatHistory([`"""
${subtitleInfos[videoPlayerProps.currentTimeIndex].subtitle}
"""
In the above sentence, ${subtitleInfos[videoPlayerProps.currentTimeIndex].question}`,
            subtitleInfos[videoPlayerProps.currentTimeIndex].answer
        ])
        
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


    const [isChatDialogOpened, setIsChatDialogOpened] = useState(false)
    const [chatHistory, setChatHistory] = useState([])

    const onChatHistorySubmit = async (userQuestion) => {
        const reqDto = {
            "messages": [...chatHistory, userQuestion]
        }
        
        console.log(`[EFFECT] Try to get AI chat response: <url:${`${APIConfig.externalSystem}/openai/getChatResponse`} messages:[${reqDto.messages}]>`)
        const response = await axios.put(`${APIConfig.externalSystem}/openai/getChatResponse`, reqDto);
        console.log("[EFFECT] AI chat response:", response)

        setChatHistory((chatHistory) => {
            return [...chatHistory, userQuestion, response.data.chatResponse]
        })
    }


    return (
        <>
        <VideoQuizTryAppBar/>

        {
            (() => {
                if (videoPlayerProps.url && videoPlayerProps.timeRanges && 
                   (videoPlayerProps.timeRanges.length > 0) && quizInfo && chatHistory.length > 0) {
                    return (
                        <>
                        <Stack spacing={0.5} sx={{marginTop: 1, marginBottom: 1}}>
                            <Card variant="outlined">
                                <CuttedVideoPlayer url={videoPlayerProps.url} currentTimeIndex={videoPlayerProps.currentTimeIndex} timeRanges={videoPlayerProps.timeRanges}/>
                            </Card>
                            <VideoPlayerControllerCard videoPlayerProps={videoPlayerProps} 
                                        onClickPrevButton={onClickPrevButton} onClickNextButton={onClickNextButton}/>


                            <VideoQuizCard videoPlayerProps={videoPlayerProps} subtitleInfos={subtitleInfos}
                                           quizInfo={quizInfo} onAllCorrect={onAllCorrect}/>


                            <QnACard videoPlayerProps={videoPlayerProps} subtitleInfos={subtitleInfos} 
                                     onClickChatButton={()=>{setIsChatDialogOpened(true);}}/>
                            <QnADialog chatHistory={chatHistory} onChatHistorySubmit={onChatHistorySubmit}
                                       open={isChatDialogOpened} onClose={()=>{setIsChatDialogOpened(false);}}/>
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