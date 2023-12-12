import React from 'react';
import { Typography, CardContent, Stack } from '@mui/material';
import BoldText from '../../../_global/text/BoldText';

const SolvedSelectQuiz = ({quizInfo, isShowtranslation}) => {
    return (
        <Stack>
            <CardContent>
                {
                    quizInfo.words.map((placedWord, index) => {
                        return (
                            <Typography key={index} sx={{color: "black", fontWeight: "bolder", fontFamily: "Ubuntufont", float: "left", padding: 0.5}}>
                                {placedWord.toUpperCase()}
                            </Typography>
                        )
                    })
                }
            </CardContent>
            {
                (isShowtranslation) ? (
                    <CardContent>
                        <BoldText sx={{float: "left", fontSize: 12}}>
                            {quizInfo.translatedSubtitle}
                        </BoldText>
                    </CardContent>
                ) : false
            }
        </Stack>
    );
}

export default SolvedSelectQuiz;