// Nav에 텍스트를 적기 위한 통합적인 Typography 컴포넌트 

import React from 'react';
import { Typography } from '@mui/material';

const NavText = ({children, sx, ...props}) => {
    return (
        <Typography sx={{color: "white", fontWeight: "bolder", fontFamily: "BMDfont", ...sx}} {...props}>
            {children}
        </Typography>
    );
}

export default NavText;