from flask import jsonify

class GetChatResponseResDto:
    def __init__(self, chatResponse:str) :
        self.__chatResponse:str = chatResponse
    

    def __str__(self) :
        return "<GetChatResponseResDto chatResponse: {}>"\
                    .format(self.__chatResponse)


    @property
    def chatResponse(self) -> str :
        return self.__chatResponse
    

    @chatResponse.setter
    def subtitles(self, chatResponse) :
        self.__chatResponse = chatResponse
    

    def json(self) -> str :
        return jsonify({
            "chatResponse": self.__chatResponse 
        })
