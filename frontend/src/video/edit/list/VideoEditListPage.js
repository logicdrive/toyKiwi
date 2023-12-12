import React, { useState, useContext, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { Typography, Card, CardContent, Grid, CardMedia, IconButton, Menu, MenuItem } from '@mui/material';
import HourglassEmptyIcon from '@mui/icons-material/HourglassEmpty';
import MoreVertIcon from '@mui/icons-material/MoreVert';
import DeleteIcon from '@mui/icons-material/Delete';
import APIConfig from '../../../APIConfig';
import { AlertPopupContext } from '../../../_global/alertPopUp/AlertPopUpContext'
import BoldText from '../../../_global/text/BoldText';
import VideoEditListAppBar from './VideoEditListAppBar';

const VideoEditListPage = () => {
    const { addAlertPopUp } = useContext(AlertPopupContext);
    const navigate = useNavigate();


    const [uploadVideos, setUploadVideos] = useState([]);
    const [isUploadVideoMenuOpeneds, setIsUploadVideoMenuOpeneds] = useState([]);
    const [anchorEl, setAnchorEl] = React.useState(null);
    
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
            console.log("Delete video Id :", videoIdToDelete);
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

    const videoStatusMap = {
        "VideoUploadRequested": "비디오 업로드중...[1/5]",
        "VideoUrlUploaded": "자막 생성중...[2/5]",
        "SubtitleMetadataUploaded": "자막 생성중...[3/5]",
        "GeneratedSubtitleUploaded": "자막 번역중...[4/5]"
    }

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
                    if(uploadVideo.status === "TranlatedSubtitleUploaded")
                    {
                        return (
                            <Grid item xs={6} key={index}>
                                <Card variant="outlined" sx={{height: 225}}>
                                    <CardContent onClick={() => {navigate(`/video/quiz/try?videoId=${uploadVideo.videoId}`)}} sx={{cursor: "pointer"}}>
                                        <CardMedia
                                            component="img"
                                            height="145"
                                            image={uploadVideo.thumbnailUrl}
                                            sx={{cursor: "pointer"}}
                                        />

                                        <BoldText sx={{marginTop: 1.4}}>
                                            {uploadVideo.videoTitle.length <= 25 ? uploadVideo.videoTitle: (uploadVideo.videoTitle.substr(0, 25) + "...")}
                                        </BoldText>
                                        <BoldText>
                                            총 문제수: {uploadVideo.subtitleCount}
                                        </BoldText>
                                    </CardContent>
                                    
                                    <IconButton aria-label="settings" sx={{float: "right", position: "relative", bottom: 67}} onClick={(e) => {setAnchorEl(e.currentTarget);setUploadVideoMenuOpened(index, true)}}>
                                        <MoreVertIcon />
                                    </IconButton>
                                    <Menu
                                        open={isUploadVideoMenuOpeneds[index]}
                                        onClose={() => {setAnchorEl(null);setUploadVideoMenuOpened(index, false)}}
                                        anchorEl={anchorEl}
                                    >
                                        <MenuItem onClick={() => {onDeleteUploadVideoButtonClicked(index)}}>
                                            <BoldText sx={{fontSize: 4}}>
                                                <DeleteIcon/>
                                            </BoldText>
                                            <BoldText sx={{marginLeft: 1}}>
                                                삭제
                                            </BoldText>
                                        </MenuItem>
                                    </Menu>
                                </Card>
                            </Grid>
                        )
                    }
                    else
                    {
                        return (
                            <Grid item xs={6} key={index}>
                                <Card variant="outlined" sx={{height: 225, cursor: "pointer"}}>
                                    <CardContent sx={{textAlign: "center", marginTop:2}}>
                                        <Typography variant="body2" color="black" sx={{fontWeight: "bolder", fontFamily: "BMDfont"}}>
                                            <HourglassEmptyIcon sx={{fontSize: 50}}/>
                                        </Typography>
                                        <br/>
                                        <Typography variant="body2" color="black" sx={{fontWeight: "bolder", fontFamily: "BMDfont"}}>
                                            {videoStatusMap[uploadVideo.status]}
                                        </Typography>
                                        <br/>
                                        <Typography variant="body2" color="black" sx={{fontWeight: "bolder", fontFamily: "BMDfont", fontSize:10}}>
                                            {uploadVideo.youtubeUrl}
                                        </Typography>
                                    </CardContent>
                                </Card>
                            </Grid>
                        )
                    }
                })
            }
        </Grid>
        </>
    );
}

export default VideoEditListPage;