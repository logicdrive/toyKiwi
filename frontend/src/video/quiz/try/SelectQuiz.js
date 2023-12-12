// 단어들이 속성들로 주어지면, 그 단어들을 섞어서 선택하게 만드는 퀴즈를 제공해주는 샘플
// 전부 성공시에는 onAllCorrect() 콜백함수로 이러한 사실을 알려줌
// useEffect를 이용해서 전부 성공시에 props.words를 바꿔서 다음 스탭으로 나아갈 수 있도록 구현함

import React, { useState, useEffect } from 'react';
import { Container, Toolbar, Link, Button, Typography, TextField, ToggleButton, Stack,
    Dialog, DialogTitle, DialogContent, DialogActions, Card, CardContent, Grid, CardMedia, IconButton, Menu, MenuItem, AppBar } from '@mui/material';

function SelectQuiz(props) {
    const shuffledSelectableWords = [...props.words].sort(() => Math.random() - 0.5)
    const [wordProps, setWordProps] = useState({
        answerWords: [...props.words],
        currentCheckIndex: 0,
        selectableWords: [...shuffledSelectableWords],
        initialSelectableWords: [...shuffledSelectableWords]
    });

    useEffect(() => {
        const shuffledSelectableWords = [...props.words].sort(() => Math.random() - 0.5)
        setWordProps({
            answerWords: [...props.words],
            currentCheckIndex: 0,
            selectableWords: [...shuffledSelectableWords],  
            initialSelectableWords: [...shuffledSelectableWords] 
        })
    }, [props.words])


    const onClickSeletableWord = (e) => {
        const selectedWordIndex = e.target.attributes.index.nodeValue;
        const selectedWord = wordProps.selectableWords[selectedWordIndex];

        if(wordProps.answerWords[wordProps.currentCheckIndex] !== selectedWord) {
            console.log("WRONG...");
            return;
        }

        if(wordProps.currentCheckIndex === wordProps.answerWords.length-1) {
            if(props.onAllCorrect) props.onAllCorrect();
        }
        setWordProps((wordProps) => {
            let copiedSelectableWords = [...wordProps.selectableWords];
            copiedSelectableWords[selectedWordIndex] = "*"+copiedSelectableWords[selectedWordIndex];

            console.log("CORRECT !");
            return {
                ...wordProps,
                currentCheckIndex: wordProps.currentCheckIndex+1,
                selectableWords: copiedSelectableWords
            }
        })
    }

    return (
    <>
        <Stack>
            <CardContent>
                {
                    wordProps.answerWords.map((placedWord, index) => {
                        if(index < wordProps.currentCheckIndex){
                            return (
                                <Typography key={index} sx={{color: "black", fontWeight: "bolder", fontFamily: "Ubuntufont", float: "left", padding: 0.5}}>
                                    {placedWord.toUpperCase()}
                                </Typography>
                            )
                        }
                        else
                            return (
                                <Typography key={index} sx={{color: "black", fontWeight: "bolder", fontFamily: "Ubuntufont", float: "left", padding: 0.5}}>
                                    {"_".repeat(wordProps.answerWords[index].length)}
                                </Typography>            
                            )
                    })
                }
            </CardContent>
            <CardContent>
                {
                    wordProps.selectableWords.map((selectableWord, index) => {
                        console.log(index)
                        if(selectableWord[0] != "*")
                            return (
                                <Button key={index} index={index} onClick={onClickSeletableWord}>
                                    <Typography index={index} sx={{color: "black", fontWeight: "bolder", fontFamily: "BMDfont", float: "left", padding: 0.5, fontSize: 10}}>
                                        {selectableWord}
                                    </Typography>
                                </Button>
                            )
                        else
                            return (
                                <Button key={index} disabled>
                                    <Typography sx={{opacity:0.1, color: "black", fontWeight: "bolder", fontFamily: "BMDfont", float: "left", padding: 0.5, fontSize: 10}}>
                                        {selectableWord.slice(1)}
                                    </Typography>
                                </Button>
                            )
                    })
                }
            </CardContent>
        </Stack>
    </>
    )
}

export default SelectQuiz;