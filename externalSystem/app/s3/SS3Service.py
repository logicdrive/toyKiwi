from .._global.logger import CustomLogger
from .._global.logger import CustomLoggerType
from .._global.workdir.WorkDirManager import WorkDirManager

from .reqDtos.UploadYoutubeVideoReqDto import UploadYoutubeVideoReqDto
from .resDtos.UploadYoutubeVideoResDto import UploadYoutubeVideoResDto

from .services.YoutubeVideoDownloadService import VideoMetadataDto, downloadCuttedYoutubeVideo


def uploadYoutubeVideo(uploadedYoutubVideoReqDto:UploadYoutubeVideoReqDto) -> UploadYoutubeVideoResDto :
    with WorkDirManager(isAfterClear=False) as path:
        videoMetadataDto:VideoMetadataDto = downloadCuttedYoutubeVideo(
            uploadedYoutubVideoReqDto.youtubeUrl, path(), "video.mp4", "thumbnail.jpg",
            uploadedYoutubVideoReqDto.cuttedStartSecond, uploadedYoutubVideoReqDto.cuttedEndSecond
        )
    
    return UploadYoutubeVideoResDto(videoMetadataDto.title, "Test upload url", "Test thumbnail Url")