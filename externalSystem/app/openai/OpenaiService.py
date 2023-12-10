import wget
import os

from .._global.workdir.WorkDirManager import WorkDirManager
from .._global.logger import CustomLogger
from .._global.logger import CustomLoggerType

from .reqDtos.GenerateSubtitleReqDto import GenerateSubtitleReqDto
from .resDtos.GenerateSubtitleResDto import GenerateSubtitleResDto
from .resDtos.SubtitleResDto import SubtitleResDto

from .services.AudioSplitService import ChunkDto, ChunkDtos, splitAudiosBaseOnSlience
from .services.OpenAIProxyService import generateAudioText

# 주어진 동영상들을 분석해서 자막들을 추출해서 관련 정보들을 반환시키기 위해서
def generateSubtitle(generateSubtitleReqDto:GenerateSubtitleReqDto) -> GenerateSubtitleResDto :
    subtitles:list = []

    with WorkDirManager() as path:
        CustomLogger.debug(CustomLoggerType.EFFECT, "Try to download video", "<downloadUrl: {}>".format(generateSubtitleReqDto.uploadedUrl))
        wget.download(generateSubtitleReqDto.uploadedUrl, path("video.mp4"))

        CustomLogger.debug(CustomLoggerType.EFFECT, "Try to make directory for audio chunks", "<path: {}>".format(path("audios/")))
        os.makedirs(path("audios"))
        chunkDtos:ChunkDtos = splitAudiosBaseOnSlience(path("video.mp4"), "mp4", path("audios"), "chunk")

        for chunckDto in chunkDtos.chunkDtos:
            chunckDto:ChunkDto = chunckDto
            audioText:str = generateAudioText(chunckDto.filePath)
            subtitles.append(SubtitleResDto(audioText, chunckDto.startSecond, chunckDto.endSecond))
    
    return GenerateSubtitleResDto(subtitles)
