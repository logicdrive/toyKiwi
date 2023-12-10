from flask import request as flaskRequest

class GenerateSubtitleReqDto:
    def __init__(self, request:flaskRequest) :
        self.__jsonData = request.get_json()
        self.__uploadedUrl:str = self.__jsonData["uploadedUrl"] or ""

    def __str__(self) :
        return "<GenerateSubtitleReqDto uploadedUrl: {}>"\
            .format(self.__uploadedUrl)


    @property
    def uploadedUrl(self) -> str :
        return self.__uploadedUrl
