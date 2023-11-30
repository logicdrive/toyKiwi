// 단어들이 속성들로 주어지면, 그 단어들을 섞어서 선택하게 만드는 퀴즈를 제공해주는 샘플
// 전부 성공시에는 onAllCorrect() 콜백함수로 이러한 사실을 알려줌
// useEffect를 이용해서 전부 성공시에 props.words를 바꿔서 다음 스탭으로 나아갈 수 있도록 구현함

import React, { useState, useEffect } from 'react';

function SelectQuiz(props) {
    const [wordProps, setWordProps] = useState({
        answerWords: [...props.words],
        currentCheckIndex: 0,
        selectableWords: [...props.words].sort(() => Math.random() - 0.5),
    });

    useEffect(() => {
        setWordProps({
            answerWords: [...props.words],
            currentCheckIndex: 0,
            selectableWords: [...props.words].sort(() => Math.random() - 0.5),   
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
            copiedSelectableWords[selectedWordIndex] = "";

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
        <h2>placedWords</h2>
        {
            wordProps.answerWords.map((placedWord, index) => {
                if(index < wordProps.currentCheckIndex){
                return (
                    <div key={index}>{placedWord}</div>
                )
                }
                else
                return (
                    <div key={index}>{"_".repeat(wordProps.answerWords[index].length)}</div>
                )
            })
        }

        <h2>selectableWords</h2>
        {
            wordProps.selectableWords.map((selectableWord, index) => {
                if(selectableWord.length > 0)
                    return (
                        <div key={index} index={index} onClick={onClickSeletableWord}>{selectableWord}</div>
                    )
                return null;
            })
        }
    </>
    )
}

export default SelectQuiz;