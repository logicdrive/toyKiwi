from flask import current_app

def debug(message) :
    current_app.logger.debug(message)