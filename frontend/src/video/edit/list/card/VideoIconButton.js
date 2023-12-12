// 업로드된 비디오에 대해서 추가적인 기능을 제공해주는 확장 버튼

import React from 'react';
import { IconButton, Menu, MenuItem } from '@mui/material';
import MoreVertIcon from '@mui/icons-material/MoreVert';
import DeleteIcon from '@mui/icons-material/Delete';
import BoldText from '../../../../_global/text/BoldText';

const VideoIconButton = ({index, isOpened, onClick, onClose, onDeleteUploadVideoButtonClicked, sx}) => {
    const [anchorEl, setAnchorEl] = React.useState(null);

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
            <MenuItem onClick={() => {onDeleteUploadVideoButtonClicked(index)}}>
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