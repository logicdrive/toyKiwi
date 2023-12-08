from flask import Blueprint, Response, request
from http import HTTPStatus

from . import SanityCheckService

from .._global import CustomLogger
from .._global import CustomLoggerType


bp = Blueprint("sanityCheck", __name__, url_prefix="/sanityCheck")

@bp.route("/", methods=("GET",))
def sanityCheck() -> str:
    CustomLogger.debug(CustomLoggerType.ENTER_EXIT)
    return Response("", status=200)

@bp.route("/logs", methods=("GET",))
def logs() -> str:
    try :

        lineLength:int = int(request.args.get("lineLength") or 10)
    
    
        CustomLogger.debug(CustomLoggerType.ENTER, "", "<lineLength: {}>".format(lineLength))

        logs = SanityCheckService.logs(lineLength)

        CustomLogger.debug(CustomLoggerType.EXIT, "", "<lineLength: {}>".format(lineLength))
        return Response(logs, status=HTTPStatus.OK)

    except Exception as e:
        CustomLogger.error(e, "", "<lineLength: {}>".format(request.args.get("lineLength") or "None"))
        return Response("", status=HTTPStatus.BAD_REQUEST)