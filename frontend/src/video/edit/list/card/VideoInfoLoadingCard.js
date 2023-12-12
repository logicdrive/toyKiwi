import React from 'react';
import { Card, CardContent } from '@mui/material';
import HourglassEmptyIcon from '@mui/icons-material/HourglassEmpty';
import BoldText from '../../../../_global/text/BoldText';

const VideoInfoLoadingCard = ({uploadVideo}) => {
    const videoStatusMap = {
        "VideoUploadRequested": "비디오 업로드중...[1/5]",
        "VideoUrlUploaded": "자막 생성중...[2/5]",
        "SubtitleMetadataUploaded": "자막 생성중...[3/5]",
        "GeneratedSubtitleUploaded": "자막 번역중...[4/5]"
    }
    
    return (
        <Card variant="outlined" sx={{height: 225, cursor: "pointer"}}>
            <CardContent sx={{textAlign: "center", marginTop:2}}>
                <BoldText sx={{color: "gray"}}>
                    <HourglassEmptyIcon sx={{fontSize: 50}}/>
                </BoldText>
                <br/>
                <BoldText sx={{color: "gray"}}>
                    {videoStatusMap[uploadVideo.status]}
                </BoldText>
                <br/>
                <BoldText sx={{color: "gray", fontSize:10}}>
                    {uploadVideo.youtubeUrl}
                </BoldText>
            </CardContent>
        </Card>
    );
}

export default VideoInfoLoadingCard;