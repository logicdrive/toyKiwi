from .._global.workdir.WorkDirManager import WorkDirManager

from .reqDtos.UploadYoutubeVideoReqDto import UploadYoutubeVideoReqDto
from .resDtos.UploadYoutubeVideoResDto import UploadYoutubeVideoResDto
from .reqDtos.RemoveFileVideoReqDto import RemoveFileVideoReqDto
from .resDtos.RemoveFileVideoResDto import RemoveFileVideoResDto

from .services.YoutubeVideoDownloadService import VideoMetadataDto, downloadCuttedYoutubeVideo
from .services.S3ProxyService import uploadToPublicS3, deleteToPublic3

# 주어진 유튜브 URL에서 동영상을 다운로드 받고, 관련 동영상 및 썸네일을 업로드해서 그 정보를 반환시키기 위해서
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

# 주어진 경로에 있는 파일을 삭제시키기 위해서
def removeFile(removeFileVideoReqDto:RemoveFileVideoReqDto) -> RemoveFileVideoResDto :
    deleteToPublic3(removeFileVideoReqDto.fileUrl.split("/")[-1])
    return RemoveFileVideoResDto(removeFileVideoReqDto.fileUrl)
