import React, { useState, useContext, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from "react-router-dom";
import { Grid } from '@mui/material';
import APIConfig from '../../../APIConfig';
import { AlertPopupContext } from '../../../_global/alertPopUp/AlertPopUpContext'
import VideoEditListAppBar from './appBar/VideoEditListAppBar';
import VideoInfoCard from './card/VideoInfoCard';
import VideoInfoLoadingCard from './card/VideoInfoLoadingCard';
import SanityCheckSocket from './socket/SanityCheckSocket';
import VideoSubscribeSocket from './socket/VideoSubscribeSocket';

const VideoEditListPage = () => {
    const {addAlertPopUp} = useContext(AlertPopupContext);
    const navigate = useNavigate();
    SanityCheckSocket();

    const [uploadVideos, setUploadVideos] = useState([]);
    const [isUploadVideoMenuOpeneds, setIsUploadVideoMenuOpeneds] = useState([]);

    
    const [videoNotifiedHandler] = useState(() => {
        return (videoId, videoStatus) => {
            if(((videoStatus === "GeneratedQnAUploaded") || 
                (videoStatus === "VideoUploadFailed") || 
                (videoStatus === "VideoRemoveRequested")))
            {
                navigate(0);
                return;
            } 
            if(!(uploadVideos.map((uploadVideo) => uploadVideo.videoId).includes(videoId))) {
                navigate(0);
                return;
            }
    
            setUploadVideos((uploadVideos) => {
                let copiedUploadVideos = []
                for(let uploadVideo of uploadVideos)
                {
                    if(uploadVideo.videoId === videoId)
                        copiedUploadVideos.push({...uploadVideo, status: videoStatus})
                    else
                        copiedUploadVideos.push({...uploadVideo})
                }

                return copiedUploadVideos
            } )
        }
    })
    const [subscribeVideoStatus] = VideoSubscribeSocket(videoNotifiedHandler);

    useEffect(() => {
        (async () => {
            try {

                console.log(`[EFFECT] Try to download video infos: <url:${`${APIConfig.collectedDataUrl}/videos`}>`)
                const response = await axios.get(`${APIConfig.collectedDataUrl}/videos`);
                const videos = response.data._embedded.videos;
                console.log("[EFFECT] Downloaded videos:", response)

                setIsUploadVideoMenuOpeneds(new Array(videos.length).fill(false))
                setUploadVideos(response.data._embedded.videos.map((video) => {
                    if(!((video.status === "GeneratedQnAUploaded") || 
                       (video.status === "VideoUploadFailed") || 
                       (video.status === "VideoRemoveRequested"))) {
                        subscribeVideoStatus(video.videoId)
                    }

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
    }, [addAlertPopUp, subscribeVideoStatus])


    // 입력된 정보를 기반으로 동영상 업로드 요청을 수행하기 위해서
    const onInputCompleted = async (userInputs) => {
        console.log("[EFFECT] Try to upload video by using userInputs:", userInputs);

        const reqDto = {
            youtubeUrl: userInputs.youtubeUrl,
            cuttedStartSecond: userInputs.cuttedStartSecond,
            cuttedEndSecond: userInputs.cuttedEndSecond
        }
        const reponse = await axios.post(`${APIConfig.videoUrl}/videos`, reqDto);


        const videoHrefSplit = reponse.data._links.self.href.split("/")
        const createdVideoId = videoHrefSplit[videoHrefSplit.length-1]
        subscribeVideoStatus(createdVideoId)
    }

    // 삭제 버튼을 누를시에 비디오 삭제 요청을 수행하기 위해서
    const onDeleteUploadVideoButtonClicked = async (index) => {
        setUploadVideoMenuOpened(index, false)

        const videoIdToDelete = uploadVideos[index].videoId
        console.log("[Effect] Try to delete video :", videoIdToDelete);
        await axios.delete(`${APIConfig.videoUrl}/videos/${videoIdToDelete}`);
    }


    const setUploadVideoMenuOpened = (index, value) => {
        setIsUploadVideoMenuOpeneds((isUploadVideoMenuOpeneds) => {
            let copiedValue = [...isUploadVideoMenuOpeneds]
            copiedValue[index] = value
            return copiedValue
        })
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
                            (uploadVideo.status === "GeneratedQnAUploaded") ? (

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