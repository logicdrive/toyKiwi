from flask import request as flaskRequest

class TranslateSubtitleReqDto:
    def __init__(self, request:flaskRequest) :
        self.__jsonData = request.get_json()
        self.__subtitle:str = self.__jsonData["subtitle"] or ""


    def __str__(self) :
        return "<TranslateSubtitleReqDto subtitle: {}>"\
            .format(self.__subtitle)


    @property
    def subtitle(self) -> str :
        return self.__subtitle
