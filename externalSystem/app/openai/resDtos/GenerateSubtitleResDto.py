from flask import jsonify

class GenerateSubtitleResDto:
    def __init__(self, subtitles:list) :
        self.__subtitles:list = subtitles
    

    def __str__(self) :
        return "<GenerateSubtitleResDto subtitles: {}>"\
                    .format("\n".join(map(str, self.__subtitles)))


    @property
    def subtitles(self) -> str :
        return self.__subtitles
    

    @subtitles.setter
    def subtitles(self, subtitles) :
        self.__subtitles = subtitles
    

    def json(self) -> str :
        return jsonify({
            "subtitles": [subtitle.dict() for subtitle in self.__subtitles]
        })
