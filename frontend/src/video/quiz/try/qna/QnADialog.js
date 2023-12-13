import React, { useState, useContext, useEffect } from 'react';
import { Card, Button, Dialog, DialogTitle, DialogContent, DialogActions, TextField } from '@mui/material';
import Stack from '@mui/material/Stack';
import PersonIcon from '@mui/icons-material/Person';
import SmartToyIcon from '@mui/icons-material/SmartToy';
import BoldText from '../../../../_global/text/BoldText';
import { AlertPopupContext } from '../../../../_global/alertPopUp/AlertPopUpContext'

const QnADialog = ({chatHistory, onChatHistorySubmit, isOpened, onClose, ...props}) => {
    const {addAlertPopUp} = useContext(AlertPopupContext);
    const [isUserQuestionEnabled, setIsUserQuestionEnabled] = useState(true);
    const [userQuestion, setUserQuestion] = useState("");

    useEffect(() => {
        setUserQuestion("")
    }, [isOpened])

    const handleChatHistorySubmit = async () => {
        setIsUserQuestionEnabled(false);

        try {
            await onChatHistorySubmit(userQuestion);
        } catch (error) {
            addAlertPopUp("AI 채팅 응답을 가져오는 과정에서 오류가 발생했습니다!", "error");
            console.error("AI 채팅 응답을 가져오는 과정에서 오류가 발생했습니다!", error);
        }

        setUserQuestion("")
        setIsUserQuestionEnabled(true);
    }

    return (
        <Dialog open={isOpened} onClose={onClose} {...props}>
            <DialogTitle sx={{color: "black", fontWeight: "bolder", fontFamily: "BMDfont"}}>AI 채팅</DialogTitle>
            <DialogContent>
                <Stack spacing={0.5}>
                    {
                        chatHistory.map((chat, index) => {
                            return (
                                <Card variant="outlined" sx={{padding: 1}} key={index}>
                                    <BoldText>
                                        {(index%2===0) ? <PersonIcon/> : <SmartToyIcon/>}
                                    </BoldText>
                                    <BoldText>
                                        {chat}
                                    </BoldText>
                                </Card>
                            )
                        })
                    }
                </Stack>
            </DialogContent>

            <DialogActions sx={{padding: 3}}>
                <TextField
                    label="추가 질문"
                    name="userQuestion"

                    value={userQuestion}
                    onChange={(e) => {setUserQuestion(e.target.value)}}

                    
                    fullWidth
                    size="small"
                    disabled={!isUserQuestionEnabled} 
                />

                <Button onClick={handleChatHistorySubmit}
                sx={{color: "black", fontWeight: "bolder", fontFamily: "BMDfont"}} disabled={!isUserQuestionEnabled}>보내기</Button>
            </DialogActions>
        </Dialog>
    );
}

export default QnADialog;