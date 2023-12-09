import os

from flask import Flask

from ._global.logger import LoggingConfig
from .sanityCheck import SanityCheckController
from .s3 import s3Controller


dirPathsToCreate = ["./logs", "./workDirs"]

def create_app():
    for dirPathToCreate in dirPathsToCreate :
        if not os.path.exists(dirPathToCreate):
            os.makedirs(dirPathToCreate)


    app = Flask(__name__)
    LoggingConfig.setupLoggingConfig()

    app.register_blueprint(SanityCheckController.bp)
    app.register_blueprint(s3Controller.bp)

    return app
