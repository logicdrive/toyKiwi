from flask import Blueprint, request
from http import HTTPStatus

from .._global.logger import CustomLogger
from .._global.logger import CustomLoggerType

from . import DeeplService
from .reqDtos.TranslateSubtitleReqDto import TranslateSubtitleReqDto
from .resDtos.TranslateSubtitleResDto import TranslateSubtitleResDto


bp = Blueprint("deepl", __name__, url_prefix="/deepl")

# 주어진 자막에 대한 한글 번역문을 반환시키기 위해서
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
