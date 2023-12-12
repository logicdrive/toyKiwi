import React from 'react';
import { useNavigate } from 'react-router-dom';
import { Button, Typography } from '@mui/material';

const ToHomeButton = ({sx}) => {
    const navigate = useNavigate();

    return (
        <Button onClick={() => {
            navigate("/video/edit/list")
        }} variant="contained" color="error" sx={{...sx}}>
            <Typography sx={{color: "white", fontWeight: "bolder", fontFamily: "BMDfont", outlineColor: "red"}}>
                돌아가기
            </Typography>
        </Button>
    );
}

export default ToHomeButton;