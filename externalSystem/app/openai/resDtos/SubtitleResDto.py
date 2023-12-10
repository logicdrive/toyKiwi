from flask import jsonify

class SubtitleResDto:
    def __init__(self, subtitle, startSecond, endSecond) :
        self.__subtitle:str = subtitle
        self.__startSecond:str = startSecond
        self.__endSecond:str = endSecond
    

    def __str__(self) :
        return "<SubtitleResDto subtitle: {}, startSecond: {}, endSecond: {}>"\
                    .format(self.__subtitle, self.__startSecond, self.__endSecond)


    @property
    def subtitle(self) -> str :
        return self.__subtitle
    
    @property
    def startSecond(self) -> str :
        return self.__startSecond
    
    @property
    def endSecond(self) -> str :
        return self.__endSecond
    

    @subtitle.setter
    def subtitle(self, subtitle) :
        self.__subtitle = subtitle

    @startSecond.setter
    def startSecond(self, startSecond) :
        self.__startSecond = startSecond

    @endSecond.setter
    def endSecond(self, endSecond) :
        self.__endSecond = endSecond
    
    
    def dict(self) -> dict :
        return {
            "subtitle": self.__subtitle,
            "startSecond": self.__startSecond,
            "endSecond": self.__endSecond
        }

    def json(self) -> str :
        return jsonify({
            "subtitle": self.__subtitle,
            "startSecond": self.__startSecond,
            "endSecond": self.__endSecond
        })
