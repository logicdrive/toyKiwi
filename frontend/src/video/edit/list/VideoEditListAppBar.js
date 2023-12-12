// 유저로부터 유튜브 URL을 입력받고, 해당 URL을 기반으로 동영상을 S3에 업로드하기 위해서

import React from 'react';
import { Container, Toolbar, Link, AppBar } from '@mui/material';
import VideoUploadButton from './VideoUploadButton';

const VideoEditListAppBar = ({onInputCompleted}) => {
    return (
        <AppBar position="static" style={{backgroundColor:"crimson"}}>
            <Container maxWidth="lg">
                <Toolbar disableGutters>
                    <Link variant="h5" underline="none" sx={{color: "white", fontWeight: "bolder", fontFamily: "BMDfont", flexGrow: 1, cursor: "default"}}>
                        학습 동영상 목록
                    </Link>

                    <VideoUploadButton onInputCompleted={onInputCompleted} sx={{position: "relative", left: 4}}/>
                </Toolbar>
            </Container>
        </AppBar>
    );
}

export default VideoEditListAppBar;