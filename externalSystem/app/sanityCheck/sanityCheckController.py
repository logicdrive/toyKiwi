from flask import Blueprint

bp = Blueprint("sanityCheck", __name__, url_prefix="/sanityCheck")


@bp.route("/")
def sanityCheck():
    return "sanityCheck"