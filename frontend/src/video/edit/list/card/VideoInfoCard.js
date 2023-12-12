import React from 'react';
import { useNavigate } from 'react-router-dom';
import { Card, CardContent, CardMedia } from '@mui/material';
import BoldText from '../../../../_global/text/BoldText';
import VideoIconButton from './VideoIconButton';

const VideoInfoCard = ({uploadVideo, isIconOpend, onClickIcon, onClickCloseIcon, onDeleteUploadVideoButtonClicked, index}) => {
    const navigate = useNavigate();
    
    return (
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

        <VideoIconButton index={index} isOpened={isIconOpend} sx={{float: "right", position: "relative", bottom: 67}}
                         onClick={onClickIcon} onClose={onClickCloseIcon}
                         onDeleteUploadVideoButtonClicked={onDeleteUploadVideoButtonClicked}/>
    </Card>
    );
}

export default VideoInfoCard;