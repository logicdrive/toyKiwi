from flask import Blueprint, request
from http import HTTPStatus

from .._global.logger import CustomLogger
from .._global.logger import CustomLoggerType

from . import OpenaiService
from .reqDtos.GenerateSubtitleReqDto import GenerateSubtitleReqDto
from .resDtos.GenerateSubtitleResDto import GenerateSubtitleResDto


bp = Blueprint("openai", __name__, url_prefix="/openai")

# 주어진 동영상들을 분석해서 자막들을 추출해서 관련 정보들을 반환시키기 위해서
@bp.route("/generateSubtitle", methods=("PUT",))
def generateSubtitle() -> GenerateSubtitleResDto :
    try :

        generateSubtitleReqDto:GenerateSubtitleReqDto = GenerateSubtitleReqDto(request)
        CustomLogger.debug(CustomLoggerType.ENTER, "", "<generateSubtitleReqDto: {}>".format(generateSubtitleReqDto))

        generateSubtitleResDto:GenerateSubtitleResDto = OpenaiService.generateSubtitle(generateSubtitleReqDto)

        CustomLogger.debug(CustomLoggerType.EXIT, "", "<generateSubtitleResDto: {}>".format(generateSubtitleResDto))
        return (generateSubtitleResDto.json(), HTTPStatus.OK)

    except Exception as e :
        jsonData = request.get_json()
        uploadedUrl = jsonData["uploadedUrl"] or ""
        CustomLogger.error(e, "", "<uploadedUrl: {}>".format(uploadedUrl))
        return ("", HTTPStatus.BAD_REQUEST)
