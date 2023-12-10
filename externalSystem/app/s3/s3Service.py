from .._global.logger import CustomLogger
from .._global.logger import CustomLoggerType
from .._global.workdir.WorkDirManager import WorkDirManager

from .reqDtos.UploadYoutubeVideoReqDto import UploadYoutubeVideoReqDto
from .resDtos.UploadYoutubeVideoResDto import UploadYoutubeVideoResDto

def uploadYoutubeVideo(uploadedYoutubVideoReqDto:UploadYoutubeVideoReqDto) -> UploadYoutubeVideoResDto :
    with WorkDirManager() as path:
        print("Used work dir:", path())
        print("Used video file path:", path("video.mp4"))
    
    return UploadYoutubeVideoResDto("Test title", "Test upload url", "Test thumbnail Url")
    