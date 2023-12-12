import React, { useState, useContext, useEffect } from 'react';
import axios from 'axios';
import { Grid } from '@mui/material';
import APIConfig from '../../../APIConfig';
import { AlertPopupContext } from '../../../_global/alertPopUp/AlertPopUpContext'
import VideoEditListAppBar from './VideoEditListAppBar';
import VideoInfoCard from './VideoInfoCard';
import VideoInfoLoadingCard from './VideoInfoLoadingCard';

const VideoEditListPage = () => {
    const { addAlertPopUp } = useContext(AlertPopupContext);


    const [uploadVideos, setUploadVideos] = useState([]);
    const [isUploadVideoMenuOpeneds, setIsUploadVideoMenuOpeneds] = useState([]);

    const setUploadVideoMenuOpened = (index, value) => {
        setIsUploadVideoMenuOpeneds((isUploadVideoMenuOpeneds) => {
            let copiedValue = [...isUploadVideoMenuOpeneds]
            copiedValue[index] = value
            return copiedValue
        })
    }

    const onDeleteUploadVideoButtonClicked = async (index) => {
        setUploadVideoMenuOpened(index, false)

        try {
            
            const videoIdToDelete = uploadVideos[index].videoId
            console.log("[Effect] Try to delete video :", videoIdToDelete);
            await axios.delete(`${APIConfig.videoUrl}/videos/${videoIdToDelete}`);

            addAlertPopUp("비디오 삭제 요청이 정상적으로 완료되었습니다.", "success");

        } catch (error) {
            addAlertPopUp("비디오 삭제 요청 과정에서 오류가 발생했습니다!", "error");
            console.error("비디오 삭제 요청 과정에서 오류가 발생했습니다!", error);
        }
    }

    useEffect(() => {
        (async () => {
            try {

                const response = await axios.get(`${APIConfig.collectedDataUrl}/videos`);
                const videos = response.data._embedded.videos;

                setIsUploadVideoMenuOpeneds(new Array(videos.length).fill(false))
                setUploadVideos(response.data._embedded.videos.map((video) => {
                    return {
                        videoId: video.videoId,
                        videoTitle: video.videoTitle,
                        youtubeUrl: video.youtubeUrl,
                        thumbnailUrl: video.thumbnailUrl,
                        subtitleCount: video.subtitleCount,
                        status: video.status
                    }
                }));

            } catch (error) {
                addAlertPopUp("업로된 동영상 목록을 가져오는 과정에서 오류가 발생했습니다!", "error");
                console.error("업로된 동영상 목록을 가져오는 과정에서 오류가 발생했습니다!", error);
            }
        })()
    }, [addAlertPopUp])

    const onInputCompleted = async (userInputs) => {
        console.log("[EFFECT] 동영상 업로드 요청이 수행됨:", userInputs);

        const reqDto = {
            youtubeUrl: userInputs.youtubeUrl,
            cuttedStartSecond: userInputs.cuttedStartSecond,
            cuttedEndSecond: userInputs.cuttedEndSecond
        }
        await axios.post(`${APIConfig.videoUrl}/videos`, reqDto);
    }
    

    return (
        <>
        <VideoEditListAppBar onInputCompleted={onInputCompleted}/>

        <Grid container spacing={2} sx={{marginTop: 0.5}}>
            {
                uploadVideos.map((uploadVideo, index) => {
                    return (
                        <Grid item xs={6} key={index}>
                        {
                            (uploadVideo.status === "TranlatedSubtitleUploaded") ? (

                                <VideoInfoCard index={index} uploadVideo={uploadVideo} isIconOpend={isUploadVideoMenuOpeneds[index]}
                                onClickIcon={() => {setUploadVideoMenuOpened(index, true)}} onClickCloseIcon={() => {setUploadVideoMenuOpened(index, false)}}
                                onDeleteUploadVideoButtonClicked={onDeleteUploadVideoButtonClicked}/>
                            
                            ) : ( <VideoInfoLoadingCard uploadVideo={uploadVideo}/> )
                        }
                        </Grid>
                    )
                })
            }
        </Grid>
        </>
    );
}

export default VideoEditListPage;