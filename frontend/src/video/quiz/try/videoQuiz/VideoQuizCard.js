import React, { useState } from 'react';
import { ToggleButton, Card, CardContent } from '@mui/material';
import TranslateIcon from '@mui/icons-material/Translate';
import SelectQuiz from './SelectQuiz';
import SolvedSelectQuiz from './SolvedSelectQuiz';
import BoldText from '../../../../_global/text/BoldText';

const VideoQuizCard = ({videoPlayerProps, subtitleInfos, quizInfo, onAllCorrect}) => {
    const [isShowtranslation, setIsShowTranslation] = useState(true)

    return (
        <Card variant="outlined" sx={{padding: 1}}>
            <CardContent sx={{height: 20, padding: 0}}>
                <BoldText sx={{float: "left"}}>
                    {`Q.${videoPlayerProps.currentTimeIndex+1}/${subtitleInfos.length}`}
                </BoldText>

                <ToggleButton value="bold" aria-label="bold" sx={{float: "right"}} selected={isShowtranslation} onChange={() => {setIsShowTranslation(!isShowtranslation)}}>
                    <TranslateIcon sx={{fontSize: 15}} />
                </ToggleButton>
            </CardContent>

            <CardContent>
                {
                    (videoPlayerProps.currentTimeIndex === videoPlayerProps.limitedTimeIndex) ? (
                        <SelectQuiz words={quizInfo.words} translatedSubtitle={quizInfo.translatedSubtitle} onAllCorrect={onAllCorrect} isShowtranslation={isShowtranslation}/>
                    ) : (
                        <SolvedSelectQuiz quizInfo={quizInfo} isShowtranslation={isShowtranslation}/>
                    )
                }
            </CardContent>
        </Card>
    );
}

export default VideoQuizCard;