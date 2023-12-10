from .reqDtos.GenereateSubtitleReqDto import GenereateSubtitleReqDto
from .resDtos.GenereateSubtitleResDto import GenereateSubtitleResDto
from .resDtos.SubtitleResDto import SubtitleResDto

# 주어진 동영상들을 분석해서 자막들을 추출해서 관련 정보들을 반환시키기 위해서
def genereateSubtitle(genereateSubtitleReqDto:GenereateSubtitleReqDto) -> GenereateSubtitleResDto :
    subtitles:list = []
    subtitles.append(SubtitleResDto("Test Subtitle No.1", 0, 5))
    subtitles.append(SubtitleResDto("Test Subtitle No.2", 5, 10))

    genereateSubtitleResDto:GenereateSubtitleResDto = GenereateSubtitleResDto(subtitles)
    return genereateSubtitleResDto
