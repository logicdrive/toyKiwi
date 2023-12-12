// 업로드된 비디오에 대해서 추가적인 기능을 제공해주는 확장 버튼

import React, { useContext } from 'react';
import { IconButton, Menu, MenuItem } from '@mui/material';
import MoreVertIcon from '@mui/icons-material/MoreVert';
import DeleteIcon from '@mui/icons-material/Delete';
import { AlertPopupContext } from '../../../../_global/alertPopUp/AlertPopUpContext'
import BoldText from '../../../../_global/text/BoldText';

const VideoIconButton = ({index, isOpened, onClick, onClose, onDeleteUploadVideoButtonClicked, sx}) => {
    const { addAlertPopUp } = useContext(AlertPopupContext);
    const [anchorEl, setAnchorEl] = React.useState(null);

    const handleOnDeleteUploadVideoButtonClicked = async (index) => {
        try {

            await onDeleteUploadVideoButtonClicked(index)
            addAlertPopUp("비디오 삭제 요청이 정상적으로 완료되었습니다.", "success");

        } catch (error) {
            addAlertPopUp("비디오 삭제 요청 과정에서 오류가 발생했습니다!", "error");
            console.error("비디오 삭제 요청 과정에서 오류가 발생했습니다!", error);
        }
    }

    return (
        <>
        <IconButton aria-label="settings" sx={{...sx}} onClick={(e) => {onClick();setAnchorEl(e.currentTarget);}}>
            <MoreVertIcon />
        </IconButton>

        <Menu
            open={isOpened}
            onClose={() => {onClose();setAnchorEl(null);}}
            anchorEl={anchorEl}
        >
            <MenuItem onClick={() => {handleOnDeleteUploadVideoButtonClicked(index)}}>
                <BoldText sx={{fontSize: 4}}>
                    <DeleteIcon/>
                </BoldText>
                <BoldText sx={{marginLeft: 1}}>
                    삭제
                </BoldText>
            </MenuItem>
        </Menu>
        </>
    );
}

export default VideoIconButton;