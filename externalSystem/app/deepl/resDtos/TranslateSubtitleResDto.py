from flask import jsonify

class TranslateSubtitleResDto:
    def __init__(self, translatedSubtitle) :
        self.__translatedSubtitle:str = translatedSubtitle


    def __str__(self) :
        return "<TranslateSubtitleResDto translatedSubtitle: {}>".format(self.__translatedSubtitle)


    @property
    def translatedSubtitle(self) -> str :
        return self.__translatedSubtitle
    

    @translatedSubtitle.setter
    def translatedSubtitle(self, translatedSubtitle) :
        self.__translatedSubtitle = translatedSubtitle
    

    def json(self) -> str :
        return jsonify({
            "translatedSubtitle": self.__translatedSubtitle,
        })
