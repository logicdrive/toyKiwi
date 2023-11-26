# OpenAI API를 활용해서 오디오를 텍스트로 변환시키는 샘플 파일

from openai import OpenAI

class OpenAIProxy :
    def __init__(self) :
        self.__CLIENT = OpenAI()
    
    def transratedAudioText(self, autoFilePath:str) -> str :
        return self.__CLIENT.audio.transcriptions.create(
            model="whisper-1", 
            file=open(autoFilePath, "rb"),
            response_format="text"
        )

openAIProxy:OpenAIProxy = OpenAIProxy()
print(openAIProxy.transratedAudioText("./tmp/tmps/chunk_2.mp4"))