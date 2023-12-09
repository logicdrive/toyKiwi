from .reqDtos.UploadYoutubeVideoReqDto import UploadYoutubeVideoReqDto
from .resDtos.UploadYoutubeVideoResDto import UploadYoutubeVideoResDto

from .._global.logger import CustomLogger
from .._global.logger import CustomLoggerType

def uploadYoutubeVideo(uploadedYoutubVideoReqDto:UploadYoutubeVideoReqDto) -> UploadYoutubeVideoResDto :
    return UploadYoutubeVideoResDto("Test title", "Test upload url", "Test thumbnail Url")