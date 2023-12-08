from flask import current_app
from . import CustomLoggerType
import traceback


customLoggerTypeMap = {
    CustomLoggerType.ENTER_EXIT: "ENTER/EXIT",
    CustomLoggerType.ENTER: "ENTER",
    CustomLoggerType.EXIT: "EXIT",
    CustomLoggerType.EFFECT: "EFFECT"
}


def debug(type:CustomLoggerType, message:str="", params:str="{}") :
    current_app.logger.debug("[{}] [{}] {}: {}".format(traceback.extract_stack()[-2], customLoggerTypeMap[type], message, params))

def error(e:Exception, message:str="", params:str="{}") :
    current_app.logger.error("[{}] [{}] {}: {}".format(traceback.extract_stack()[-2], e.__class__, message, params) + "\n" + "\n".join(map(str, traceback.extract_stack())))