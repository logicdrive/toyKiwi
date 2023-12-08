from flask import Blueprint, request
from http import HTTPStatus

from . import SanityCheckService
from .LogsReqDto import LogsReqDto
from .LogsResDto import LogsResDto
from .EchoWithJsonReqDto import EchoWithJsonReqDto
from .EchoWithJsonResDto import EchoWithJsonResDto

from .._global import CustomLogger
from .._global import CustomLoggerType


bp = Blueprint("sanityCheck", __name__, url_prefix="/sanityCheck")

@bp.route("/", methods=("GET",))
def sanityCheck() -> str :
    CustomLogger.debug(CustomLoggerType.ENTER_EXIT)
    return ("", HTTPStatus.OK)

@bp.route("/logs", methods=("GET",))
def logs() -> str :
    try :

        logsReqDto:LogsReqDto = LogsReqDto(request)


        CustomLogger.debug(CustomLoggerType.ENTER, "", "<logsReqDto: {}>".format(logsReqDto))

        logs = SanityCheckService.logs(logsReqDto)

        CustomLogger.debug(CustomLoggerType.EXIT, "", "<logsReqDto: {}>".format(logsReqDto))
        return (LogsResDto(logs).json(), HTTPStatus.OK)

    except Exception as e :
        CustomLogger.error(e, "", "<lineLength: {}>".format(request.args.get("lineLength") or "None"))
        return ("", HTTPStatus.BAD_REQUEST)

# JSON 송수신 여부를 간편하게 테스트해보기 위해서
@bp.route("/echoWithJson", methods=("PUT",))
def echoWithJson() -> str :
    try :

        echoWithJsonReqDto:EchoWithJsonReqDto = EchoWithJsonReqDto(request)
        CustomLogger.debug(CustomLoggerType.ENTER_EXIT, "", "<echoWithJsonReqDto: {}>".format(echoWithJsonReqDto))
        return (EchoWithJsonResDto(echoWithJsonReqDto.message).json(), HTTPStatus.OK)

    except Exception as e :
        CustomLogger.error(e, "", "<message: {}>".format(request.args.get("message") or "None"))
        return ("", HTTPStatus.BAD_REQUEST)
