import React, { useState, useContext, useEffect } from 'react';
import axios from 'axios';
import { useSearchParams } from 'react-router-dom';
import { Container, Toolbar, Link, Button, Typography, TextField, ToggleButton,
    Dialog, DialogTitle, DialogContent, DialogActions, Card, CardContent, Grid, CardMedia, IconButton, Menu, MenuItem, AppBar } from '@mui/material';
import { AlertPopupContext } from '../../../_global/alertPopUp/AlertPopUpContext'
import APIConfig from '../../../APIConfig';
import { Link as RouterLink, useNavigate } from 'react-router-dom';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import CuttedVideoPlayer from './CuttedVideoPlayer';
import SkipPreviousIcon from '@mui/icons-material/SkipPrevious';
import SkipNextIcon from '@mui/icons-material/SkipNext';
import Stack from '@mui/material/Stack';
import SelectQuiz from './SelectQuiz';
import TranslateIcon from '@mui/icons-material/Translate';

const VideoQuizTryPage = () => {
    const { addAlertPopUp } = useContext(AlertPopupContext);
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
                console.log(response)

            } catch (error) {
                addAlertPopUp("업로된 동영상 자막 정보를 가져오는 과정에서 오류가 발생했습니다!", "error");
                console.error("업로된 동영상 자막 정보를 가져오는 과정에서 오류가 발생했습니다!", error);
            }
        })()
    }, [addAlertPopUp])

    useEffect(() => {
        setVideoPlayerProps({
            url: uploadVideoInfo.uploadedUrl,
            currentTimeIndex: 0,
            timeRanges: subtitleInfos.map((subtitleInfo) => {
                return {
                    startTimeSec: subtitleInfo.startSecond,
                    endTimeSec: subtitleInfo.endSecond
                }
            })
        })
    }, [uploadVideoInfo, subtitleInfos])

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
        if(videoPlayerProps.currentTimeIndex === videoPlayerProps.timeRanges.length-1) {
          return
        }
    
        setVideoPlayerProps((videoPlayerProps) => {
          return {
            ...videoPlayerProps,
            currentTimeIndex: videoPlayerProps.currentTimeIndex+1
          }
        })
    }
    

    const [quizInfo, setQuizInfo] = useState()

    useEffect(() => {
        if(subtitleInfos && subtitleInfos.length > 0)
        setQuizInfo({
            words: subtitleInfos[0].subtitle.split(" ")
        })
    }, [subtitleInfos])

    const onAllCorrect = () => {
        console.log("Correct !!!")
    }

    return (
        <>
        <AppBar position="static" style={{backgroundColor:"crimson"}}>
            <Container maxWidth="lg">
                <Toolbar disableGutters>
                    <Link variant="h5" underline="none" sx={{color: "white", fontWeight: "bolder", fontFamily: "BMDfont", flexGrow: 1, cursor: "default"}}>
                        퀴즈
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
            (() => {
                if (videoPlayerProps.url && videoPlayerProps.timeRanges && 
                   (videoPlayerProps.timeRanges.length > 0) && quizInfo) {
                    return (
                        <>
                        <Stack spacing={0.5} sx={{marginTop: 1}}>
                            <Card variant="outlined">
                                <CuttedVideoPlayer url={videoPlayerProps.url} currentTimeIndex={videoPlayerProps.currentTimeIndex} timeRanges={videoPlayerProps.timeRanges}/>
                            </Card>
                            
                            <Card variant="outlined">
                                <Button onClick={onClickPrevButton} sx={{float:"left", color: "black"}}>
                                    <SkipPreviousIcon/>
                                </Button>
                                <Button onClick={onClickNextButton} sx={{float: "right", color: "black"}}>
                                    <SkipNextIcon/>
                                </Button>
                            </Card>

                            <Card variant="outlined" sx={{padding: 1}}>
                                <CardContent sx={{height: 20, padding: 0}}>
                                    <Typography sx={{color: "black", fontWeight: "bolder", fontFamily: "BMDfont", float: "left"}}>
                                        {`Q.${videoPlayerProps.currentTimeIndex+1}/${subtitleInfos.length}`}
                                    </Typography>
                                    <ToggleButton value="bold" aria-label="bold" sx={{float: "right"}}>
                                        <TranslateIcon sx={{fontSize: 15}} />
                                    </ToggleButton>
                                </CardContent>
                                <CardContent>
                                <SelectQuiz words={quizInfo.words} onAllCorrect={onAllCorrect}/>
                                </CardContent>
                            </Card>
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