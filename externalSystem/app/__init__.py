from flask import Flask
from .sanityCheck import sanityCheckController
from .config import logging

def create_app():
    app = Flask(__name__)
    logging.setupLoggingConfig()

    app.register_blueprint(sanityCheckController.bp)

    return app