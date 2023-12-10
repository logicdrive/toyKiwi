import wget

from .._global.workdir.WorkDirManager import WorkDirManager
from .._global.logger import CustomLogger
from .._global.logger import CustomLoggerType

from .reqDtos.GenerateSubtitleReqDto import GenerateSubtitleReqDto
from .resDtos.GenerateSubtitleResDto import GenerateSubtitleResDto
from .resDtos.SubtitleResDto import SubtitleResDto

# 주어진 동영상들을 분석해서 자막들을 추출해서 관련 정보들을 반환시키기 위해서
def generateSubtitle(generateSubtitleReqDto:GenerateSubtitleReqDto) -> GenerateSubtitleResDto :
    with WorkDirManager(isAfterClear=False) as path:
        CustomLogger.debug(CustomLoggerType.EFFECT, "Try to download video", "<downloadUrl: {}>".format(generateSubtitleReqDto.uploadedUrl))
        wget.download(generateSubtitleReqDto.uploadedUrl, path("video.mp4"))
    
    subtitles:list = []
    subtitles.append(SubtitleResDto("Test Subtitle No.1", 0, 5))
    subtitles.append(SubtitleResDto("Test Subtitle No.2", 5, 10))

    generateSubtitleResDto:GenerateSubtitleResDto = GenerateSubtitleResDto(subtitles)
    return generateSubtitleResDto
