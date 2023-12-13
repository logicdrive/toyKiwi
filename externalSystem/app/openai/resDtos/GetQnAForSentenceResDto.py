from flask import jsonify

class GetQnAForSentenceResDto:
    def __init__(self, question:str, answer:str) :
        self.__question:str = question
        self.__answer:str = answer
    

    def __str__(self) :
        return "<GetQnAForSentenceResDto question: {}, answer: {}>"\
                    .format(self.__question, self.__answer)


    @property
    def question(self) -> str :
        return self.__question
    
    @property
    def answer(self) -> str :
        return self.__answer
    

    @question.setter
    def subtitles(self, question) :
        self.__question = question

    @answer.setter
    def subtitles(self, answer) :
        self.__answer = answer
    

    def json(self) -> str :
        return jsonify({
            "question": self.__question,
            "answer": self.__answer
        })
