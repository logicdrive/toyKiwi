from flask import jsonify

class RemoveFileVideoResDto:
    def __init__(self, fileUrl:str) :
        self.__fileUrl:str = fileUrl
    

    def __str__(self) :
        return "<RemoveFileVideoResDto fileUrl: {}>".format(self.__fileUrl)


    @property
    def fileUrl(self) -> str :
        return self.__fileUrl
    

    @fileUrl.setter
    def fileUrl(self, fileUrl) :
        self.__fileUrl = fileUrl
    

    def json(self) -> str :
        return jsonify({
            "fileUrl": self.__fileUrl
        })
