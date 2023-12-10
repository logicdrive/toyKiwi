from flask import Blueprint, request
from http import HTTPStatus

from .._global.logger import CustomLogger
from .._global.logger import CustomLoggerType

from . import OpenaiService
from .reqDtos.GenereateSubtitleReqDto import GenereateSubtitleReqDto
from .resDtos.GenereateSubtitleResDto import GenereateSubtitleResDto


bp = Blueprint("openai", __name__, url_prefix="/openai")

# 주어진 유튜브 URL에서 동영상을 다운로드 받고, 관련 동영상 및 썸네일을 업로드해서 그 정보를 반환시키기 위해서
@bp.route("/genereateSubtitle", methods=("PUT",))
def genereateSubtitle() -> GenereateSubtitleResDto :
    try :

        genereateSubtitleReqDto:GenereateSubtitleReqDto = GenereateSubtitleReqDto(request)
        CustomLogger.debug(CustomLoggerType.ENTER, "", "<genereateSubtitleReqDto: {}>".format(genereateSubtitleReqDto))

        genereateSubtitleResDto:GenereateSubtitleResDto = OpenaiService.genereateSubtitle(genereateSubtitleReqDto)

        CustomLogger.debug(CustomLoggerType.EXIT, "", "<genereateSubtitleResDto: {}>".format(genereateSubtitleResDto))
        return (genereateSubtitleResDto.json(), HTTPStatus.OK)

    except Exception as e :
        jsonData = request.get_json()
        uploadedUrl = jsonData["uploadedUrl"] or ""
        CustomLogger.error(e, "", "<uploadedUrl: {}>".format(uploadedUrl))
        return ("", HTTPStatus.BAD_REQUEST)
