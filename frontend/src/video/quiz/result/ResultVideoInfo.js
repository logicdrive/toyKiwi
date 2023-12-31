import React from 'react';
import { Stack, CardMedia } from '@mui/material';
import StarIcon from '@mui/icons-material/Star';
import StarBorderIcon from '@mui/icons-material/StarBorder';
import BoldText from '../../../_global/text/BoldText';

const ResultVideoInfo = ({uploadVideoInfo, correctedWordCount, inCorrectedWordCount}) => {
    const correctPct = Math.floor((correctedWordCount/(correctedWordCount + inCorrectedWordCount)) * 100)
    const starCount = Math.floor(correctPct/33)

    return (
        <Stack>
            <CardMedia
                    component="img"
                    height="200"
                    image={uploadVideoInfo.thumbnailUrl}
            />
            <BoldText sx={{marginTop: 1.4}}>
                {uploadVideoInfo.videoTitle.length <= 50 ? uploadVideoInfo.videoTitle: (uploadVideoInfo.videoTitle.substr(0, 50) + "...")}
            </BoldText>

            <BoldText sx={{marginTop: 1.4}}>
                {
                    (new Array(starCount).fill(null)).map((_, index) => {
                        return (<StarIcon key={index}/>)
                    })
                }
                {
                    (new Array(3-starCount).fill(null)).map((_, index) => {
                        return (<StarBorderIcon key={index}/>)
                    })
                }
            </BoldText>
        
            <BoldText>
                {`${correctPct}%`}
            </BoldText>
        </Stack>
    );
}

export default ResultVideoInfo;