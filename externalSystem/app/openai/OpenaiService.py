from .reqDtos.GenereateSubtitleReqDto import GenereateSubtitleReqDto
from .resDtos.GenereateSubtitleResDto import GenereateSubtitleResDto
from .resDtos.SubtitleResDto import SubtitleResDto

def genereateSubtitle(genereateSubtitleReqDto:GenereateSubtitleReqDto) -> GenereateSubtitleResDto :
    subtitles:list = []
    subtitles.append(SubtitleResDto("Test Subtitle 1", 0, 5))
    subtitles.append(SubtitleResDto("Test Subtitle 2", 5, 10))

    genereateSubtitleResDto:GenereateSubtitleResDto = GenereateSubtitleResDto(subtitles)
    return genereateSubtitleResDto