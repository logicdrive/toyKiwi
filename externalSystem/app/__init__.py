from flask import Flask
from .sanityCheck import sanityCheckController

def create_app():
    app = Flask(__name__)

    app.register_blueprint(sanityCheckController.bp)

    return app