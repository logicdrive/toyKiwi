from flask import Blueprint, current_app

bp = Blueprint("sanityCheck", __name__, url_prefix="/sanityCheck")


@bp.route("/")
def sanityCheck():
    current_app.logger.debug("Debug Message")
    return "sanityCheck"