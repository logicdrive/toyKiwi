import React from 'react';
import { Card, Button } from '@mui/material';
import QuestionAnswerIcon from '@mui/icons-material/QuestionAnswer';
import BoldText from '../../../_global/text/BoldText';

const QnACard = ({videoPlayerProps, subtitleInfos, onClickChatButton, ...props}) => {
    return (
        <Card variant="outlined" sx={{padding: 1}} {...props}>
            <BoldText sx={{fontSize: 12, float: "left", marginTop: 1}}>
                {subtitleInfos[videoPlayerProps.currentTimeIndex].question.length <= 60 ? subtitleInfos[videoPlayerProps.currentTimeIndex].question :
                (subtitleInfos[videoPlayerProps.currentTimeIndex].question.substr(0, 60) + "...")}
            </BoldText>
            <Button sx={{float: "right"}} onClick={onClickChatButton}>
                <QuestionAnswerIcon sx={{color: "black", fontSize: 20}}/>
            </Button>
        </Card>

    );
}

export default QnACard;