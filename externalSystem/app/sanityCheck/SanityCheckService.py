import re

from .reqDtos.LogsReqDto import LogsReqDto
from .resDtos.LogsResDto import LogsResDto

from .._global.logger import CustomLogger
from .._global.logger import CustomLoggerType


logFilePath:str = "./logs/logback.log"

def logs(logsReqDto:LogsReqDto) -> LogsResDto :
    readLogs:list[str] = []


    CustomLogger.debug(CustomLoggerType.EFFECT, "", "<filePath: {}>".format(logFilePath))

    with open(logFilePath, 'r') as f:
        for readline in f.readlines() :
            if logsReqDto.regFilter : 
                if re.match(logsReqDto.regFilter, readline) : readLogs.append(readline)
            else : 
                readLogs.append(readline)

    CustomLogger.debug(CustomLoggerType.EFFECT, "", "<readLogsSize: {}>".format(len(readLogs)))


    return LogsResDto(readLogs)
