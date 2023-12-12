// 유저로부터 유튜브 URL을 입력받고, 해당 URL을 기반으로 동영상을 S3에 업로드하기 위해서

import React, { useState, useContext } from 'react';
import { Button, TextField, Dialog, DialogTitle, DialogContent, DialogActions } from '@mui/material';
import FileUploadIcon from '@mui/icons-material/FileUpload';
import { AlertPopupContext } from '../../../_global/alertPopUp/AlertPopUpContext'
import NavButton from '../../../_global/button/NavButton';

const VideoUploadButton = ({onInputCompleted, sx, ...props}) => {
    const { addAlertPopUp } = useContext(AlertPopupContext);


    const [isVideoUploadDialogOpend, setIsVideoUploadDialogOpend] = useState(false)
    const [videoUploadInfo, setVideoUploadInfo] = useState({
        youtubeUrl: "", cuttedStartSecond: 0, cuttedEndSecond: 0
    })

    const onVideoUploadButtonClicked = () => {
        setVideoUploadInfo({
            youtubeUrl: "", cuttedStartSecond: 0, cuttedEndSecond: 0
        })
        setIsVideoUploadDialogOpend(true)
    }
    const handleVideoUploadInfoChange = (event) => {
        const { name, value } = event.target;
        setVideoUploadInfo((prevData) => ({
            ...prevData,
            [name]: value,
        }));
    }
    const handleVideoUploadInfoSubmit = async () => {
        try {
            await onInputCompleted({
                youtubeUrl: videoUploadInfo.youtubeUrl,
                cuttedStartSecond: Number(videoUploadInfo.cuttedStartSecond),
                cuttedEndSecond: Number(videoUploadInfo.cuttedEndSecond)
            })

            addAlertPopUp("비디오 업로드 요청이 정상적으로 완료되었습니다.", "success");

        } catch (error) {
            addAlertPopUp("비디오 업로드 요청 과정에서 오류가 발생했습니다!", "error");
            console.error("비디오 업로드 요청 과정에서 오류가 발생했습니다!", error);
        }
    }


    return (
        <>
        <NavButton onClick={onVideoUploadButtonClicked} sx={{...sx}} {...props}>
            <FileUploadIcon sx={{fontSize: 40}}/>
        </NavButton>

        <Dialog open={isVideoUploadDialogOpend} onClose={()=>{setIsVideoUploadDialogOpend(false);}}>
            <DialogTitle sx={{color: "black", fontWeight: "bolder", fontFamily: "BMDfont"}}>유튜브 비디오 업로드</DialogTitle>
            <DialogContent>
                <TextField
                    label="유튜브 URL"
                    name="youtubeUrl"

                    value={videoUploadInfo.youtubeUrl}
                    onChange={handleVideoUploadInfoChange}

                    margin="normal"
                    fullWidth
                />
                <TextField
                    label="시작 시간(초)"
                    name="cuttedStartSecond"

                    value={videoUploadInfo.cuttedStartSecond}
                    onChange={handleVideoUploadInfoChange}

                    margin="normal"
                    fullWidth
                    required
                    type="number"
                />
                <TextField
                    label="종료 시간(초)"
                    name="cuttedEndSecond"

                    value={videoUploadInfo.cuttedEndSecond}
                    onChange={handleVideoUploadInfoChange}

                    margin="normal"
                    fullWidth
                    required
                    type="number"
                />
            </DialogContent>

            <DialogActions>
                <Button onClick={() => {
                    handleVideoUploadInfoSubmit();
                    setIsVideoUploadDialogOpend(false);
                }} sx={{color: "black", fontWeight: "bolder", fontFamily: "BMDfont"}}>추가</Button>
            </DialogActions>
        </Dialog>
        </>
    );
}

export default VideoUploadButton;