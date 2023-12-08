from .LogsReqDto import LogsReqDto

from .._global import CustomLogger


def logs(logsReqDto:LogsReqDto) -> list[str] :
    return [str(logsReqDto.lineLength)]*logsReqDto.lineLength