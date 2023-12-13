import React from 'react';
import { Card, CardContent } from '@mui/material';
import HourglassEmptyIcon from '@mui/icons-material/HourglassEmpty';
import BoldText from '../../../../_global/text/BoldText';

const VideoInfoLoadingCard = ({uploadVideo}) => {
    const videoStatusMap = {
        "VideoUploadRequested": "비디오 업로드중...[1/6]",
        "VideoUrlUploaded": "자막 생성중...[2/6]",
        "SubtitleMetadataUploaded": "자막 생성중...[3/6]",
        "GeneratedSubtitleUploaded": "자막 번역중...[4/6]",
        "TranlatedSubtitleUploaded": "질문 및 응답 생성중...[5/6]"
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