from flask import jsonify

class LogsResDto:
    def __init__(self, logs:list[str]) :
        self.__logs:int = logs

    def __str__(self) :
        return "<LogsResDto logs: {}>".format(self.__logs)


    @property
    def logs(self) -> list[str] :
        return self.__logs
    
    
    def json(self) -> str :
        return jsonify({
            "logs": self.__logs
        })