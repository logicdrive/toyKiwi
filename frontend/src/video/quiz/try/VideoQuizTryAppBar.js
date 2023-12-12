import React from 'react';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import TopAppBar from '../../../_global/TopAppBar';
import NavNavigationButtion from '../../../_global/button/NavNavigationButton';

const VideoQuizTryAppBar = () => {
    return (
        <TopAppBar title="퀴즈">
            <NavNavigationButtion url="/video/edit/list" sx={{position: "relative", left: 6}}>
                <ArrowBackIcon sx={{fontSize: 40}}/>
            </NavNavigationButtion>
        </TopAppBar>
    );
}

export default VideoQuizTryAppBar;