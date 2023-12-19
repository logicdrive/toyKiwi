import React from 'react';
import { Container, Toolbar, Link, AppBar } from '@mui/material';
import SettingButton from './button/SettingButton';

const TopAppBar = ({children, title}) => {
    return (
        <AppBar position="static" style={{backgroundColor:"crimson"}}>
            <Container maxWidth="lg">
                <Toolbar disableGutters>
                    <Link variant="h5" underline="none" sx={{color: "white", fontWeight: "bolder", fontFamily: "BMDfont", flexGrow: 1, cursor: "default"}}>
                        {title}
                    </Link>

                    {children}

                    <SettingButton/>
                </Toolbar>
            </Container>
        </AppBar>
    );
}

export default TopAppBar;