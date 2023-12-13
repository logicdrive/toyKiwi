from flask import Blueprint, request
from http import HTTPStatus

from .._global.logger import CustomLogger
from .._global.logger import CustomLoggerType

from . import OpenaiService
from .reqDtos.GenerateSubtitleReqDto import GenerateSubtitleReqDto
from .resDtos.GenerateSubtitleResDto import GenerateSubtitleResDto
from .reqDtos.GetQnAForSentenceReqDto import GetQnAForSentenceReqDto
from .resDtos.GetQnAForSentenceResDto import GetQnAForSentenceResDto
from .reqDtos.GetChatResponseReqDto import GetChatResponseReqDto
from .resDtos.GetChatResponseResDto import GetChatResponseResDto

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

# 주어진 문장에 대한 질문 및 답변을 생성하기 위해서
@bp.route("/getQnAForSentence", methods=("PUT",))
def getQnAForSentence() -> GetQnAForSentenceResDto :
    try :

        getQnAForSentenceReqDto:GetQnAForSentenceReqDto = GetQnAForSentenceReqDto(request)
        CustomLogger.debug(CustomLoggerType.ENTER, "", "<getQnAForSentenceReqDto: {}>".format(getQnAForSentenceReqDto))

        getQnAForSentenceResDto:GetQnAForSentenceResDto = OpenaiService.getQnAForSentence(getQnAForSentenceReqDto)

        CustomLogger.debug(CustomLoggerType.EXIT, "", "<getQnAForSentenceResDto: {}>".format(getQnAForSentenceResDto))
        return (getQnAForSentenceResDto.json(), HTTPStatus.OK)

    except Exception as e :
        jsonData = request.get_json()
        sentence = jsonData["sentence"] or ""
        CustomLogger.error(e, "", "<sentence: {}>".format(sentence))
        return ("", HTTPStatus.BAD_REQUEST)

# 주어진 채팅 내역에 대한 답변을 생성하기 위해서
@bp.route("/getChatResponse", methods=("PUT",))
def getChatResponse() -> GetChatResponseResDto :
    try :

        getChatResponseReqDto:GetChatResponseReqDto = GetChatResponseReqDto(request)
        CustomLogger.debug(CustomLoggerType.ENTER, "", "<getChatResponseReqDto: {}>".format(getChatResponseReqDto))

        getChatResponseResDto:GetChatResponseResDto = OpenaiService.getChatResponse(getChatResponseReqDto)

        CustomLogger.debug(CustomLoggerType.EXIT, "", "<getChatResponseResDto: {}>".format(getChatResponseResDto))
        return (getChatResponseResDto.json(), HTTPStatus.OK)

    except Exception as e :
        jsonData = request.get_json()
        messages = jsonData["messages"] or ""
        CustomLogger.error(e, "", "<messages: {}>".format(messages))
        return ("", HTTPStatus.BAD_REQUEST)