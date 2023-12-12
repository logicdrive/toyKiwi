import React from 'react';
import NavButton from './NavButton';
import { useNavigate } from 'react-router-dom';

const NavNavigationButtion = ({children, url, sx, ...props}) => {
    const navigate = useNavigate();

    return (
        <NavButton onClick={() => {navigate(url)}} sx={{...sx}} {...props}>
            {children}
        </NavButton>
    );
}

export default NavNavigationButtion;