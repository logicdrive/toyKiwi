from flask import Blueprint, request
from http import HTTPStatus

from .._global.logger import CustomLogger
from .._global.logger import CustomLoggerType

from . import S3Service
from .reqDtos.UploadYoutubeVideoReqDto import UploadYoutubeVideoReqDto
from .resDtos.UploadYoutubeVideoResDto import UploadYoutubeVideoResDto
from .reqDtos.RemoveFileVideoReqDto import RemoveFileVideoReqDto
from .resDtos.RemoveFileVideoResDto import RemoveFileVideoResDto


bp = Blueprint("s3", __name__, url_prefix="/s3")

# 주어진 유튜브 URL에서 동영상을 다운로드 받고, 관련 동영상 및 썸네일을 업로드해서 그 정보를 반환시키기 위해서
@bp.route("/uploadYoutubeVideo", methods=("PUT",))
def uploadYoutubeVideo() -> UploadYoutubeVideoResDto :
    try :

        uploadedYoutubVideoReqDto:UploadYoutubeVideoReqDto = UploadYoutubeVideoReqDto(request)
        CustomLogger.debug(CustomLoggerType.ENTER, "", "<uploadedYoutubVideoReqDto: {}>".format(uploadedYoutubVideoReqDto))

        uploadedYoutubVideoResDto:UploadYoutubeVideoResDto = S3Service.uploadYoutubeVideo(uploadedYoutubVideoReqDto)

        CustomLogger.debug(CustomLoggerType.EXIT, "", "<uploadedYoutubVideoResDto: {}>".format(uploadedYoutubVideoResDto))
        return (uploadedYoutubVideoResDto.json(), HTTPStatus.OK)

    except Exception as e :
        jsonData = request.get_json()
        youtubeUrl = jsonData["youtubeUrl"] or ""
        cuttedStartSecond = jsonData["cuttedStartSecond"] or 0
        cuttedEndSecond = jsonData["cuttedEndSecond"] or 0
        CustomLogger.error(e, "", "<genereateSubtitleReqDto: {}, cuttedStartSecond: {}, cuttedEndSecond: {}>"
                                    .format(youtubeUrl, cuttedStartSecond, cuttedEndSecond))
        return ("", HTTPStatus.BAD_REQUEST)

# 주어진 경로에 있는 파일을 삭제시키기 위해서
@bp.route("/removeFile", methods=("PUT",))
def removeFile() -> RemoveFileVideoResDto :
    try :

        removeFileVideoReqDto:RemoveFileVideoReqDto = RemoveFileVideoReqDto(request)
        CustomLogger.debug(CustomLoggerType.ENTER, "", "<removeFileVideoReqDto: {}>".format(removeFileVideoReqDto))

        removeFileVideoResDto:RemoveFileVideoResDto = S3Service.removeFile(removeFileVideoReqDto)
        
        CustomLogger.debug(CustomLoggerType.EXIT, "", "<removeFileVideoResDto: {}>".format(removeFileVideoResDto))
        return (removeFileVideoResDto.json(), HTTPStatus.OK)

    except Exception as e :
        jsonData = request.get_json()
        fileUrl = jsonData["fileUrl"] or ""
        CustomLogger.error(e, "", "<fileUrl: {}>".format(fileUrl))
        return ("", HTTPStatus.BAD_REQUEST)
