import React, { useState, useContext, useEffect } from 'react';
import axios from 'axios';
import { Container, Toolbar, Link, Button, Typography, TextField, ToggleButton,
    Dialog, DialogTitle, DialogContent, DialogActions, Card, CardContent, Grid, CardMedia, IconButton, Menu, MenuItem, AppBar } from '@mui/material';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import { Link as RouterLink, useNavigate } from 'react-router-dom';
import { useSearchParams } from 'react-router-dom';
import { AlertPopupContext } from '../../../_global/alertPopUp/AlertPopUpContext'
import APIConfig from '../../../APIConfig';

// http://localhost:3000/video/quiz/result?videoId=1&correctedWordCount=27&inCorrectedWordCount=5
const VideoQuizResultPage = () => {
    const { addAlertPopUp } = useContext(AlertPopupContext);
    const [queryParameters] = useSearchParams()
    const navigate = useNavigate();

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
    // queryParameters.get("videoId")
    console.log(uploadVideoInfo)

    return (
        <>
        <AppBar position="static" style={{backgroundColor:"crimson"}}>
            <Container maxWidth="lg">
                <Toolbar disableGutters>
                    <Link variant="h5" underline="none" sx={{color: "white", fontWeight: "bolder", fontFamily: "BMDfont", flexGrow: 1, cursor: "default"}}>
                        퀴즈 결과
                    </Link>

                    <Link sx={{backgroundColor: "red", margin: 1, position: "relative", left: 4}}>
                        <Button onClick={() => {
                            navigate("/video/edit/list")
                        }}>
                            <Typography sx={{color: "white", fontWeight: "bolder", fontFamily: "BMDfont", position: "relative", top: 3}}>
                                <ArrowBackIcon sx={{fontSize: 40}}/>
                            </Typography>
                        </Button>
                    </Link>
                </Toolbar>
            </Container>
        </AppBar>
        </>
    );
}

export default VideoQuizResultPage;