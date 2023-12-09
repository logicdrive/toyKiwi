from flask import request as flaskRequest

class UploadYoutubeVideoReqDto:
    def __init__(self, request:flaskRequest) :
        self.__jsonData = request.get_json()
        self.__youtubeUrl:str = self.__jsonData["youtubeUrl"] or ""

    def __str__(self) :
        return "<UploadYoutubeVideoReqDto youtubeUrl: {}>".format(self.__youtubeUrl)


    @property
    def youtubeUrl(self) -> str :
        return self.__youtubeUrl
