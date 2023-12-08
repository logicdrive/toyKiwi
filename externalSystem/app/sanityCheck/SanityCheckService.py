from .LogsReqDto import LogsReqDto

from .._global import CustomLogger
from .._global import CustomLoggerType


logFilePath:str = "./logs/logback.log"

def logs(logsReqDto:LogsReqDto) -> list[str] :
    readLogs:list[str] = []


    CustomLogger.debug(CustomLoggerType.EFFECT, "", "<filePath: {}>".format(logFilePath))

    with open(logFilePath, 'r') as f:
        readLogs = f.readlines()[-logsReqDto.lineLength:]

    CustomLogger.debug(CustomLoggerType.EFFECT, "", "<readLogsSize: {}>".format(len(readLogs)))


    return readLogs