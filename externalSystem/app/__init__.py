import os
from flask import Flask

from ._global.logger import LoggingConfig

from .sanityCheck import SanityCheckController
from .s3 import S3Controller
from .openai import OpenaiController
from .deepl import DeeplController


dirPathsToCreate = ["./logs", "./workDirs"]

def create_app():
    for dirPathToCreate in dirPathsToCreate :
        if not os.path.exists(dirPathToCreate):
            os.makedirs(dirPathToCreate)


    app = Flask(__name__)
    LoggingConfig.setupLoggingConfig()

    app.register_blueprint(SanityCheckController.bp)
    app.register_blueprint(S3Controller.bp)
    app.register_blueprint(OpenaiController.bp)
    app.register_blueprint(DeeplController.bp)

    return app
