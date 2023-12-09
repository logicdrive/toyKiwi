import os

from flask import Flask

from ._global.logger import LoggingConfig
from .sanityCheck import SanityCheckController


loggingDirPath = "./logs"

def create_app():
    if not os.path.exists(loggingDirPath):
        os.makedirs(loggingDirPath)


    app = Flask(__name__)
    LoggingConfig.setupLoggingConfig()

    app.register_blueprint(SanityCheckController.bp)

    return app
