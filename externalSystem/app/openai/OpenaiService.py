import wget
import os

from .._global.workdir.WorkDirManager import WorkDirManager
from .._global.logger import CustomLogger
from .._global.logger import CustomLoggerType

from .reqDtos.GenerateSubtitleReqDto import GenerateSubtitleReqDto
from .resDtos.GenerateSubtitleResDto import GenerateSubtitleResDto
from .resDtos.SubtitleResDto import SubtitleResDto
from .reqDtos.GetQnAForSentenceReqDto import GetQnAForSentenceReqDto
from .resDtos.GetQnAForSentenceResDto import GetQnAForSentenceResDto
from .reqDtos.GetChatResponseReqDto import GetChatResponseReqDto
from .resDtos.GetChatResponseResDto import GetChatResponseResDto

from .services.AudioSplitService import ChunkDto, ChunkDtos, splitAudiosBaseOnSlience
from .services.OpenAIProxyService import generateAudioText, getQnAForSentenceByUsingChatGPT

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

# 주어진 문장에 대한 질문 및 답변을 생성하기 위해서
def getQnAForSentence(getQnAForSentenceReqDto:GetQnAForSentenceReqDto) -> GetQnAForSentenceResDto :
    question, answer = getQnAForSentenceByUsingChatGPT(getQnAForSentenceReqDto.sentence)
    return GetQnAForSentenceResDto(question, answer)

# 주어진 채팅 내역에 대한 답변을 생성하기 위해서
def getChatResponse(getChatResponseReqDto:GetChatResponseReqDto) -> GetChatResponseResDto :
    return GetChatResponseResDto("getChatResponse RES")