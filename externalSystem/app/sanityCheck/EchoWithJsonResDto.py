from flask import jsonify

class EchoWithJsonResDto:
    def __init__(self, message:str) :
        self.__message:int = message

    def __str__(self) :
        return "<EchoWithJsonResDto message: {}>".format(self.__message)


    @property
    def message(self) -> str :
        return self.__message
    
    
    def json(self) -> str :
        return jsonify({
            "message": self.__message
        })
