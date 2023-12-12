import React, { useState, useContext, useEffect } from 'react';
import axios from 'axios';
import { Stack, Card } from '@mui/material';
import { useSearchParams } from 'react-router-dom';
import { AlertPopupContext } from '../../../_global/alertPopUp/AlertPopUpContext'
import VideoQuizResultAppBar from './VideoQuizResultAppBar';
import APIConfig from '../../../APIConfig';
import ToHomeButton from './ToHomeButton';
import ResultVideoInfo from './ResultVideoInfo';

// 예시 URL: http://localhost:3000/video/quiz/result?videoId=1&correctedWordCount=27&inCorrectedWordCount=5
const VideoQuizResultPage = () => {
    const {addAlertPopUp} = useContext(AlertPopupContext);
    const [queryParameters] = useSearchParams()

    const [uploadVideoInfo, setUploadVideoInfo] = useState({});
    useEffect(() => {
        (async () => {
            try {

                const response = await axios.get(`${APIConfig.collectedDataUrl}/videos/${queryParameters.get("videoId")}`);
                setUploadVideoInfo({
                    videoId: response.data.videoId,
                    videoTitle: response.data.videoTitle,
                    thumbnailUrl: response.data.thumbnailUrl
                })

            } catch (error) {
                addAlertPopUp("업로된 동영상 정보를 가져오는 과정에서 오류가 발생했습니다!", "error");
                console.error("업로된 동영상 정보를 가져오는 과정에서 오류가 발생했습니다!", error);
            }
        })()
    }, [addAlertPopUp, queryParameters])

    return (
        <>
        <VideoQuizResultAppBar />
        
        {
            (uploadVideoInfo.videoId) ? (
                <Card variant="outlined" sx={{marginTop: 1, padding: 5, textAlign: "center"}}>
                    <Stack>
                        <ResultVideoInfo uploadVideoInfo={uploadVideoInfo} 
                                         correctedWordCount={Number(queryParameters.get("correctedWordCount"))}
                                         inCorrectedWordCount={Number(queryParameters.get("inCorrectedWordCount"))}/>

                        <ToHomeButton sx={{backgroundColor: "crimson", marginTop: 5}}/>
                    </Stack>
                </Card>
            ) : false
        }
        </>
    );
}

export default VideoQuizResultPage;