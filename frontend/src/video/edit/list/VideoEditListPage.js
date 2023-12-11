import React, { useState, useContext } from 'react';
import AppBar from '@mui/material/AppBar';
import { Link as RouterLink } from 'react-router-dom';
import { Container, Toolbar, Link, Button, Typography, TextField,
    Dialog, DialogTitle, DialogContent, DialogActions } from '@mui/material';
import FileUploadIcon from '@mui/icons-material/FileUpload';
import { AlertPopupContext } from "../../../_global/alertPopUp/AlertPopUpContext"

const VideoEditListPage = () => {
    const { addAlertPopUp } = useContext(AlertPopupContext);
    
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
    const handleVideoUploadInfoSubmit = () => {
        addAlertPopUp("비디오 업로드 요청이 정상적으로 완료되었습니다.", "success");
        console.log(videoUploadInfo)
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
        </>
    );
}

export default VideoEditListPage;