from flask import Blueprint, request
from http import HTTPStatus

from .._global.logger import CustomLogger
from .._global.logger import CustomLoggerType

from . import DeeplService
from .reqDtos.TranslateSubtitleReqDto import TranslateSubtitleReqDto
from .resDtos.TranslateSubtitleResDto import TranslateSubtitleResDto


bp = Blueprint("deepl", __name__, url_prefix="/deepl")

# 주어진 유튜브 URL에서 동영상을 다운로드 받고, 관련 동영상 및 썸네일을 업로드해서 그 정보를 반환시키기 위해서
@bp.route("/translateSubtitle", methods=("PUT",))
def genereateSubtitle() -> TranslateSubtitleResDto :
    try :

        translateSubtitleReqDto:TranslateSubtitleReqDto = TranslateSubtitleReqDto(request)
        CustomLogger.debug(CustomLoggerType.ENTER, "", "<translateSubtitleReqDto: {}>".format(translateSubtitleReqDto))

        translateSubtitleResDto:TranslateSubtitleResDto = DeeplService.genereateSubtitle(translateSubtitleReqDto)

        CustomLogger.debug(CustomLoggerType.EXIT, "", "<translateSubtitleResDto: {}>".format(translateSubtitleResDto))
        return (translateSubtitleResDto.json(), HTTPStatus.OK)

    except Exception as e :
        jsonData = request.get_json()
        subtitle = jsonData["subtitle"] or ""
        CustomLogger.error(e, "", "<subtitle: {}>".format(subtitle))
        return ("", HTTPStatus.BAD_REQUEST)
