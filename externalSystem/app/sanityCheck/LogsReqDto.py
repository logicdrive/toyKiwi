from flask import request as flaskRequest

class LogsReqDto:
    def __init__(self, request:flaskRequest) :
        self.__lineLength:int = request.args.get("lineLength") or 10

    def __str__(self) :
        return "<LogsReqDto lineLength: {}>".format(self.__lineLength)


    @property
    def lineLength(self) -> int :
        return self.__lineLength
