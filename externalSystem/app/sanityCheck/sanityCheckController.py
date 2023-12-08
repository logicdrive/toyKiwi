from flask import Blueprint, request
from http import HTTPStatus

from . import SanityCheckService
from .LogsReqDto import LogsReqDto
from .LogsResDto import LogsResDto

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