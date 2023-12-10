from flask import current_app
import traceback

from . import CustomLoggerType


customLoggerTypeMap = {
    CustomLoggerType.ENTER_EXIT: "ENTER/EXIT",
    CustomLoggerType.ENTER: "ENTER",
    CustomLoggerType.EXIT: "EXIT",
    CustomLoggerType.EFFECT: "EFFECT"
}


def debug(type:CustomLoggerType, message:str="", params:str="{}") :
    current_app.logger.debug("[{}] [{}] {}: {}".format(traceback.extract_stack()[-2], customLoggerTypeMap[type], message, params))

def error(e:Exception, message:str="", params:str="{}") :
    current_app.logger.error("[{}] [{}] {}: {}".format(traceback.extract_stack()[-2], e.__class__, e.__doc__ + " / " + message, params) + "\n" + ''.join(traceback.format_tb(e.__traceback__)))
