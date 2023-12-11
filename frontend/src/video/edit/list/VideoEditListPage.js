import React, { useState, useContext, useEffect } from 'react';
import axios from 'axios';
import AppBar from '@mui/material/AppBar';
import { Link as RouterLink, useNavigate } from 'react-router-dom';
import { Container, Toolbar, Link, Button, Typography, TextField,
    Dialog, DialogTitle, DialogContent, DialogActions, Card, CardContent, Grid, CardMedia, IconButton, Menu, MenuItem } from '@mui/material';
import FileUploadIcon from '@mui/icons-material/FileUpload';
import { AlertPopupContext } from "../../../_global/alertPopUp/AlertPopUpContext"
import APIConfig from '../../../APIConfig';
import HourglassEmptyIcon from '@mui/icons-material/HourglassEmpty';
import MoreVertIcon from '@mui/icons-material/MoreVert';
import DeleteIcon from '@mui/icons-material/Delete';

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

                console.log(videos)
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
                console.error("업로된 동영상 목록을 가져오는 과정에서 오류가 발생했습니다!", error);
            }
        })()
    }, [])

    const videoStatusMap = {
        "VideoUploadRequested": "비디오 업로드중...[1/5]",
        "VideoUrlUploaded": "자막 생성중...[2/5]",
        "SubtitleMetadataUploaded": "자막 생성중...[3/5]",
        "GeneratedSubtitleUploaded": "자막 번역중...[4/5]"
    }
    

    const [isVideoUploadDialogOpend, setIsVideoUploadDialogOpend] = useState(false)
    const [videoUploadInfo, setVideoUploadInfo] = useState({
        youtubeUrl: "", cuttedStartSecond: 0, cuttedEndSecond: 0
    })
    const handleVideoUploadInfoChange = (event) => {
        const { name, value } = event.target;
        setVideoUploadInfo((prevData) => ({
            ...prevData,
            [name]: value,
        }));
    }
    const onVideoUploadButtonClicked = () => {
        setVideoUploadInfo({
            youtubeUrl: "", cuttedStartSecond: 0, cuttedEndSecond: 0
        })
        setIsVideoUploadDialogOpend(true)
    }
    const handleVideoUploadInfoSubmit = async () => {
        try {
            
            const reqDto = {
                "youtubeUrl": videoUploadInfo.youtubeUrl,
                "cuttedStartSecond": Number(videoUploadInfo.cuttedStartSecond),
                "cuttedEndSecond": Number(videoUploadInfo.cuttedEndSecond)
            }
            console.log(reqDto)
            await axios.post(`${APIConfig.videoUrl}/videos`, reqDto);

            addAlertPopUp("비디오 업로드 요청이 정상적으로 완료되었습니다.", "success");

        } catch (error) {
            addAlertPopUp("비디오 업로드 요청 과정에서 오류가 발생했습니다!", "error");
            console.error("비디오 업로드 요청 과정에서 오류가 발생했습니다!", error);
        }
    }
    

    return (
        <>
        <AppBar position="static" style={{backgroundColor:"crimson"}}>
            <Container maxWidth="lg">
                <Toolbar disableGutters>
                    <Link component={RouterLink} to="/" variant="h5" underline="none" sx={{color: "white", fontWeight: "bolder", fontFamily: "BMDfont", flexGrow: 1}}>
                        학습 동영상 목록
                    </Link>

                    <Link component={RouterLink} sx={{backgroundColor: "red", margin: 1, position: "relative", left: 4}}>
                        <Button onClick={onVideoUploadButtonClicked}>
                            <Typography sx={{color: "white", fontWeight: "bolder", fontFamily: "BMDfont", position: "relative", top: 3}}>
                                <FileUploadIcon sx={{fontSize: 40}}/>
                            </Typography>
                        </Button>
                    </Link>
                </Toolbar>
            </Container>
        </AppBar>
        <Dialog open={isVideoUploadDialogOpend} onClose={()=>{setIsVideoUploadDialogOpend(false);}}>
            <DialogTitle sx={{color: "black", fontWeight: "bolder", fontFamily: "BMDfont"}}>유튜브 비디오 업로드</DialogTitle>
            <DialogContent>
                <TextField
                    label="유튜브 URL"
                    name="youtubeUrl"

                    value={videoUploadInfo.youtubeUrl}
                    onChange={handleVideoUploadInfoChange}

                    margin="normal"
                    fullWidth
                />
                <TextField
                    label="시작 시간(초)"
                    name="cuttedStartSecond"

                    value={videoUploadInfo.cuttedStartSecond}
                    onChange={handleVideoUploadInfoChange}

                    margin="normal"
                    fullWidth
                    required
                    type="number"
                />
                <TextField
                    label="종료 시간(초)"
                    name="cuttedEndSecond"

                    value={videoUploadInfo.cuttedEndSecond}
                    onChange={handleVideoUploadInfoChange}

                    margin="normal"
                    fullWidth
                    required
                    type="number"
                />
            </DialogContent>
            <DialogActions>
                <Button onClick={() => {
                    handleVideoUploadInfoSubmit();
                    setIsVideoUploadDialogOpend(false);
                }} sx={{color: "black", fontWeight: "bolder", fontFamily: "BMDfont"}}>추가</Button>
            </DialogActions>
        </Dialog>

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

                                        <Typography variant="body2" color="text.secondary" sx={{fontWeight: "bolder", fontFamily: "BMDfont", marginTop: 1.4}}>
                                            {uploadVideo.videoTitle.length <= 25 ? uploadVideo.videoTitle: (uploadVideo.videoTitle.substr(0, 25) + "...")}
                                        </Typography>
                                        <Typography variant="body2" color="text.secondary" sx={{fontWeight: "bolder", fontFamily: "BMDfont"}}>
                                            총 문제수: {uploadVideo.subtitleCount}
                                        </Typography>
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
                                            <Typography variant="body2" color="text.secondary" sx={{fontWeight: "bolder", fontFamily: "BMDfont", fontSize: 4}}>
                                                <DeleteIcon/>
                                            </Typography>
                                            <Typography variant="body2" color="text.secondary" sx={{fontWeight: "bolder", fontFamily: "BMDfont", marginLeft: 1}}>
                                                삭제
                                            </Typography>
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
                                        <Typography variant="body2" color="text.secondary" sx={{fontWeight: "bolder", fontFamily: "BMDfont"}}>
                                            <HourglassEmptyIcon sx={{fontSize: 50}}/>
                                        </Typography>
                                        <br/>
                                        <Typography variant="body2" color="text.secondary" sx={{fontWeight: "bolder", fontFamily: "BMDfont"}}>
                                            {videoStatusMap[uploadVideo.status]}
                                        </Typography>
                                        <br/>
                                        <Typography variant="body2" color="text.secondary" sx={{fontWeight: "bolder", fontFamily: "BMDfont", fontSize:10}}>
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