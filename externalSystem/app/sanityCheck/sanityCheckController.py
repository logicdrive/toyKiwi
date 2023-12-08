from flask import Blueprint
from . import SanityCheckService

bp = Blueprint("sanityCheck", __name__, url_prefix="/sanityCheck")

@bp.route("/")
def sanityCheck() -> str:
    SanityCheckService.sanityCheck()
    return "sanityCheck"