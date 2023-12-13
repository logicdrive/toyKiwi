from flask import request as flaskRequest

class GetQnAForSentenceReqDto:
    def __init__(self, request:flaskRequest) :
        self.__jsonData = request.get_json()
        self.__sentence:str = self.__jsonData["sentence"] or ""

    def __str__(self) :
        return "<GetQnAForSentenceReqDto sentence: {}>"\
            .format(self.__sentence)


    @property
    def sentence(self) -> str :
        return self.__sentence
