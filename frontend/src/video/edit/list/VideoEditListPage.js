import React from 'react';
import AppBar from '@mui/material/AppBar';
import { Link as RouterLink } from 'react-router-dom';
import { Container, Toolbar, Link, Button, Typography } from '@mui/material';
import FileUploadIcon from '@mui/icons-material/FileUpload';

const VideoEditListPage = () => {
    return (
        <AppBar position="static" style={{backgroundColor:"crimson"}}>
            <Container maxWidth="lg">
                <Toolbar disableGutters>
                    <Link component={RouterLink} to="/" variant="h5" underline="none" sx={{color: "white", fontWeight: "bolder", fontFamily: "BMDfont", flexGrow: 1}}>
                        학습 동영상 목록
                    </Link>

                    <Link component={RouterLink} sx={{backgroundColor: "red", margin: 1, position: "relative", left: 4}}>
                        <Button>
                            <Typography sx={{color: "white", fontWeight: "bolder", fontFamily: "BMDfont", position: "relative", top: 3}}>
                                <FileUploadIcon sx={{fontSize: 40}}/>
                            </Typography>
                        </Button>
                    </Link>
                </Toolbar>
            </Container>

            
        </AppBar>
    );
}

export default VideoEditListPage;