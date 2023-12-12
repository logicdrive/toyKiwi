import React from 'react';
import VideoUploadButton from './VideoUploadButton';
import TopAppBar from '../../../../_global/TopAppBar';

const VideoEditListAppBar = ({onInputCompleted}) => {
    return (
        <TopAppBar title="학습 동영상 목록">
            <VideoUploadButton onInputCompleted={onInputCompleted} sx={{position: "relative", left: 4}}/>
        </TopAppBar>
    );
}

export default VideoEditListAppBar;