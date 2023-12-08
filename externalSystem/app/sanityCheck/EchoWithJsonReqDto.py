from flask import request as flaskRequest

class EchoWithJsonReqDto:
    def __init__(self, request:flaskRequest) :
        self.__jsonData = request.get_json()
        self.__message:str = self.__jsonData["message"] or ""

    def __str__(self) :
        return "<EchoWithJsonReqDto message: {}>".format(self.__message)


    @property
    def message(self) -> str :
        return self.__message
