from dataclasses import dataclass
from pydub import AudioSegment
from pydub.silence import split_on_silence

from ..._global.logger import CustomLogger
from ..._global.logger import CustomLoggerType

@dataclass
class ChunkDto :
    filePath: str
    startSecond: int
    endSecond: int

    def __str__(self) :
        return "<ChunkDto filePath: {}, startSecond: {}, endSecond: {}>".format(self.filePath, self.startSecond, self.endSecond)

class ChunkDtos :
    def __init__(self, chunkDtos:list) :
        self.__size:int = len(chunkDtos)
        self.__chunkDtos:list = chunkDtos
    

    @property
    def size(self) -> int :
        return self.__size
    
    @property
    def chunkDtos(self) -> list :
        return self.__chunkDtos
    
    @size.setter
    def size(self, size) :
        self.__size = size
    
    @chunkDtos.setter
    def chunkDtos(self, chunkDtos) :
        self.__chunkDtos = chunkDtos
    

    def __str__(self) :
        return "<ChunkDtos size: {}, chunkDtos: {}>".format(self.__size, "\n".join(map(str, self.__chunkDtos)))

# 특정 비디오의 오디오를 묵음 기간을 기준으로 분리시켜서 오디오들을 저장하기 위해서
def splitAudiosBaseOnSlience(targetFilePath:str, targetFileFormat:str, chunkDirPath:str, chunkFlieName:str,
                             minSilenceLen:int=250, silenceThresh:int=-40, keepSlience:bool=True) -> ChunkDtos:
    CustomLogger.debug(CustomLoggerType.EFFECT, "Read audio from file", "<targetFilePath: {}, targetFileFormat:{}>".format(targetFilePath, targetFileFormat))
    audioSegment = AudioSegment.from_file(targetFilePath, format=targetFileFormat)
    audioChunks = split_on_silence(audioSegment, min_silence_len=minSilenceLen, silence_thresh=silenceThresh, keep_silence=keepSlience)
    
    chunckDtos:list = []
    currentMiliSecond = 0
    for i, chunk in enumerate(audioChunks):
        chunkPath = f"{chunkDirPath}/{chunkFlieName}_{i}.mp4"
        CustomLogger.debug(CustomLoggerType.EFFECT, "Write chunk file", "<chunkPath: {}>".format(chunkPath))
        chunk.export(chunkPath, format=targetFileFormat)

        chunkDto:ChunkDto = ChunkDto(
            chunkPath, int(currentMiliSecond/1000), int((currentMiliSecond+len(chunk))/1000)+1
        )
        chunckDtos.append(chunkDto)
        currentMiliSecond += len(chunk)
    
    return ChunkDtos(chunckDtos)
