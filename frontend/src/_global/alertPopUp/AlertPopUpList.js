import React, { useContext } from 'react';
import { Stack, Alert } from "@mui/material";
import { AlertPopupContext } from './AlertPopUpContext';


const AlertPopUpList = () => {
    const { alertPopupState, deleteAlertPopUp } = useContext(AlertPopupContext)

    return (
        <Stack style={{position: "fixed", right: "10px", bottom: "10px"}} spacing={1}>
            {
                alertPopupState.alertPopUps.map((alertPopup) => (
                    <Alert key={alertPopup.id} severity={alertPopup.type} 
                        onClose={() => {deleteAlertPopUp(alertPopup.id)}}>
                        {alertPopup.message}
                    </Alert>
                ))
            }
        </Stack>
    )
}

export default AlertPopUpList;