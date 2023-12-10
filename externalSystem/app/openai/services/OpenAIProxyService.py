from openai import OpenAI

from ..._global.logger import CustomLogger
from ..._global.logger import CustomLoggerType

CLIENT = OpenAI()

# 주어진 오디오 파일에서 자막을 생성시키기 위해서
def generateAudioText(autoFilePath:str) -> str :
    CustomLogger.debug(CustomLoggerType.EFFECT, "Try to generate audio text", "<autoFilePath: {}>".format(autoFilePath))
    
    audioFile = open(autoFilePath, "rb")
    audioText = CLIENT.audio.transcriptions.create(
        model="whisper-1", 
        file=audioFile,
        response_format="text"
    )
    audioFile.close()
    
    CustomLogger.debug(CustomLoggerType.EFFECT, "Audio text generated", "<audioText: {}>".format(audioText))
    return audioText