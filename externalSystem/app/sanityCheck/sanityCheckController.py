from flask import Blueprint, Response
from . import SanityCheckService
from .._global import CustomLogger
from .._global import CustomLoggerType

bp = Blueprint("sanityCheck", __name__, url_prefix="/sanityCheck")

@bp.route("/")
def sanityCheck() -> str:
    CustomLogger.debug(CustomLoggerType.ENTER_EXIT)
    CustomLogger.error(Exception("EXCEPTION"), "EXCEPTION TEST", "<test:{}>".format("TEST VALUE"))
    return Response("", status=200)