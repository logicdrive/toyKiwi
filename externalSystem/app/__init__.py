from flask import Flask
from .config import Logging
from .sanityCheck import SanityCheckController

def create_app():
    app = Flask(__name__)
    Logging.setupLoggingConfig()

    app.register_blueprint(SanityCheckController.bp)

    return app
