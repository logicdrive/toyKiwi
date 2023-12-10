from .._global.workdir.WorkDirManager import WorkDirManager

from .reqDtos.UploadYoutubeVideoReqDto import UploadYoutubeVideoReqDto
from .resDtos.UploadYoutubeVideoResDto import UploadYoutubeVideoResDto

from .services.YoutubeVideoDownloadService import VideoMetadataDto, downloadCuttedYoutubeVideo
from .services.S3ProxyService import uploadToPublicS3


def uploadYoutubeVideo(uploadedYoutubVideoReqDto:UploadYoutubeVideoReqDto) -> UploadYoutubeVideoResDto :
    uploadYoutubeVideoResDto:UploadYoutubeVideoResDto = UploadYoutubeVideoResDto()

    with WorkDirManager() as path:
        videoMetadataDto:VideoMetadataDto = downloadCuttedYoutubeVideo(
            uploadedYoutubVideoReqDto.youtubeUrl, path(), "video.mp4", "thumbnail.jpg",
            uploadedYoutubVideoReqDto.cuttedStartSecond, uploadedYoutubVideoReqDto.cuttedEndSecond
        )

        uploadYoutubeVideoResDto.videoTitle = videoMetadataDto.title
        uploadYoutubeVideoResDto.uploadedUrl = uploadToPublicS3(videoMetadataDto.outputVideoPath)
        uploadYoutubeVideoResDto.thumbnailUrl = uploadToPublicS3(videoMetadataDto.outputThumbnailPath)
    
    return uploadYoutubeVideoResDto