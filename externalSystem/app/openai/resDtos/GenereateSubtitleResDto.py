from flask import jsonify

class GenereateSubtitleResDto:
    def __init__(self, subtlties:list) :
        self.__subtlties:list = subtlties
    

    def __str__(self) :
        return "<GenereateSubtitleResDto subtlties: {}>"\
                    .format("\n".join(map(str, self.__subtlties)))


    @property
    def subtlties(self) -> str :
        return self.__subtlties
    

    @subtlties.setter
    def subtlties(self, subtlties) :
        self.__subtlties = subtlties
    

    def json(self) -> str :
        return jsonify({
            "subtlties": [subtitle.dict() for subtitle in self.__subtlties]
        })
