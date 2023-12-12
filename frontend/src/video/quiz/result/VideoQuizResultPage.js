import React, { useState, useContext, useEffect } from 'react';
import axios from 'axios';
import { Container, Toolbar, Link, Button, Typography, Stack, Card, CardMedia, AppBar } from '@mui/material';
import { useNavigate, useSearchParams } from 'react-router-dom';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import StarIcon from '@mui/icons-material/Star';
import StarBorderIcon from '@mui/icons-material/StarBorder';
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

    const correctedWordCount = Number(queryParameters.get("correctedWordCount"))
    const inCorrectedWordCount = Number(queryParameters.get("inCorrectedWordCount"))
    const correctPct = Math.floor((correctedWordCount/(correctedWordCount + inCorrectedWordCount)) * 100)
    const starCount = Math.floor(correctPct/33)

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
        {
            (uploadVideoInfo.videoId) ? (
                <Card variant="outlined" sx={{marginTop: 1, padding: 5, textAlign: "center"}}>
                <Stack>
                    <CardMedia
                            component="img"
                            height="200"
                            image={uploadVideoInfo.thumbnailUrl}
                        />
                        <Typography variant="body2" color="black" sx={{fontWeight: "bolder", fontFamily: "BMDfont", marginTop: 1.4}}>
                            {uploadVideoInfo.videoTitle.length <= 50 ? uploadVideoInfo.videoTitle: (uploadVideoInfo.videoTitle.substr(0, 50) + "...")}
                        </Typography>
    
                        <Typography variant="body2" color="black" sx={{fontWeight: "bolder", fontFamily: "BMDfont", marginTop: 1.4}}>
                            {
                                (new Array(starCount).fill(null)).map((_, index) => {
                                    return (
                                        <StarIcon key={index}/>
                                    )
                                })
                            }
                            {
                                (new Array(3-starCount).fill(null)).map((_, index) => {
                                    return (
                                        <StarBorderIcon key={index}/>
                                    )
                                })
                            }
                        </Typography>

                        <Typography variant="body2" color="black" sx={{fontWeight: "bolder", fontFamily: "BMDfont"}}>
                            {`${correctPct}%`}
                        </Typography>

                        <Button onClick={() => {
                            navigate("/video/edit/list")
                        }} variant="contained" color="error" sx={{backgroundColor: "crimson", marginTop: 5}}>
                            <Typography sx={{color: "white", fontWeight: "bolder", fontFamily: "BMDfont", outlineColor: "red"}}>
                                돌아가기
                            </Typography>
                        </Button>
                </Stack>
            </Card>
            ) : false
        }
        </>
    );
}

export default VideoQuizResultPage;