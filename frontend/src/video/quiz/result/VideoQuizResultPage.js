import React from 'react';
import { Container, Toolbar, Link, Button, Typography, TextField, ToggleButton,
    Dialog, DialogTitle, DialogContent, DialogActions, Card, CardContent, Grid, CardMedia, IconButton, Menu, MenuItem, AppBar } from '@mui/material';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import { Link as RouterLink, useNavigate } from 'react-router-dom';
import { useSearchParams } from 'react-router-dom';

// http://localhost:3000/video/quiz/result?correctedWordCount=27&inCorrectedWordCount=5
const VideoQuizResultPage = () => {
    const [queryParameters] = useSearchParams()
    const navigate = useNavigate();
    // queryParameters.get("videoId")

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