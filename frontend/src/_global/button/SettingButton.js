import React, { useState, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { Button, TextField, Dialog, DialogTitle, DialogContent, DialogActions } from '@mui/material';
import SettingsIcon from '@mui/icons-material/Settings';
import APIConfig from '../../APIConfig';
import { AlertPopupContext } from '../alertPopUp/AlertPopUpContext'
import NavButton from './NavButton';

const SettingButton = ({sx, ...props}) => {
    const {addAlertPopUp} = useContext(AlertPopupContext);
    const navigate = useNavigate();

    const [isSettingInfoDialogOpend, setIsSettingInfoDialogOpend] = useState(false)
    const [settingInfo, setSettingInfo] = useState({
        gatewayUrl: APIConfig.gatewayUrl
    })

    const onSettingInfoButtonClicked = () => {
        setIsSettingInfoDialogOpend(true)
    }
    const handleSettingInfoChange = (event) => {
        const { name, value } = event.target;
        setSettingInfo((prevData) => ({
            ...prevData,
            [name]: value,
        }));
    }
    const handleSettingInfoSubmit = () => {
        APIConfig.gatewayUrl = settingInfo.gatewayUrl;
        localStorage.setItem("toykiwi_gateway_url", settingInfo.gatewayUrl);

        navigate(0);
        addAlertPopUp("설정 변경이 정상적으로 완료되었습니다.", "success");
    }


    return (
        <>
        <NavButton onClick={onSettingInfoButtonClicked} sx={{...sx}} {...props}>
            <SettingsIcon sx={{fontSize: 40}}/>
        </NavButton>

        <Dialog open={isSettingInfoDialogOpend} onClose={()=>{setIsSettingInfoDialogOpend(false);}}>
            <DialogTitle sx={{color: "black", fontWeight: "bolder", fontFamily: "BMDfont"}}>설정 변경</DialogTitle>
            <DialogContent>
                <TextField
                    label="게이트웨이 URL"
                    name="gatewayUrl"

                    value={settingInfo.gatewayUrl}
                    onChange={handleSettingInfoChange}

                    margin="normal"
                    fullWidth
                />
            </DialogContent>

            <DialogActions>
                <Button onClick={() => {
                    handleSettingInfoSubmit();
                    setIsSettingInfoDialogOpend(false);
                }} sx={{color: "black", fontWeight: "bolder", fontFamily: "BMDfont"}}>저장</Button>
            </DialogActions>
        </Dialog>
        </>
    );
}

export default SettingButton;