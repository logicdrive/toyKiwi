from flask import request as flaskRequest

class UploadYoutubeVideoReqDto:
    def __init__(self, request:flaskRequest) :
        self.__jsonData = request.get_json()
        self.__youtubeUrl:str = self.__jsonData["youtubeUrl"] or ""
        self.__cuttedStartSecond:str = self.__jsonData["cuttedStartSecond"] or 0
        self.__cuttedEndSecond:str = self.__jsonData["cuttedEndSecond"] or 0

    def __str__(self) :
        return "<UploadYoutubeVideoReqDto youtubeUrl: {}, cuttedStartSecond: {}, cuttedEndSecond: {}>"\
            .format(self.__youtubeUrl, self.__cuttedStartSecond, self.__cuttedEndSecond)


    @property
    def youtubeUrl(self) -> str :
        return self.__youtubeUrl
    
    @property
    def cuttedStartSecond(self) -> str :
        return self.__cuttedStartSecond
    
    @property
    def cuttedEndSecond(self) -> str :
        return self.__cuttedEndSecond
