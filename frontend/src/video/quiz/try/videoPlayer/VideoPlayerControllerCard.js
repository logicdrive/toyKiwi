import React from 'react';
import { Button, Card } from '@mui/material';
import SkipPreviousIcon from '@mui/icons-material/SkipPrevious';
import SkipNextIcon from '@mui/icons-material/SkipNext';

const VideoPlayerControllerCard = ({videoPlayerProps, onClickPrevButton, onClickNextButton}) => {
    return (
        <Card variant="outlined">
            <Button onClick={onClickPrevButton} sx={{float:"left", color: "black"}}>
                <SkipPreviousIcon/>
            </Button>
            {
                (videoPlayerProps.currentTimeIndex >= videoPlayerProps.limitedTimeIndex) ? (
                    <Button onClick={onClickNextButton} sx={{float: "right", color: "black"}} disabled>
                        <SkipNextIcon/>
                    </Button>
                ) : (
                    <Button onClick={onClickNextButton} sx={{float: "right", color: "black"}}>
                        <SkipNextIcon/>
                    </Button>
                )

            }
        </Card>
    );
}

export default VideoPlayerControllerCard;