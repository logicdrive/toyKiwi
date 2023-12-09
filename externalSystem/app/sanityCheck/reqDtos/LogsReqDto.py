from flask import request as flaskRequest

class LogsReqDto:
    def __init__(self, request:flaskRequest) :
        self.__lineLength:int = request.args.get("lineLength") or 10
        self.__regFilter:str = request.args.get("regFilter") or ""

    def __str__(self) :
        return "<LogsReqDto lineLength: {}, regFilter: {}>".format(self.__lineLength, self.__regFilter)


    @property
    def lineLength(self) -> int :
        return self.__lineLength

    @property
    def regFilter(self) -> int :
        return self.__regFilter