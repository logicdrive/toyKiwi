from flask import request as flaskRequest

class GetChatResponseReqDto:
    def __init__(self, request:flaskRequest) :
        self.__jsonData = request.get_json()
        self.__messages:list[str] = self.__jsonData["messages"] or ""

    def __str__(self) :
        return "<GetChatResponseReqDto messages: {}>"\
            .format("\n".join(self.__messages))


    @property
    def messages(self) -> str :
        return self.__messages
