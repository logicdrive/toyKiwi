from .reqDtos.TranslateSubtitleReqDto import TranslateSubtitleReqDto
from .resDtos.TranslateSubtitleResDto import TranslateSubtitleResDto

from .services.DeeplProxyService import translateText

# 주어진 자막에 대한 한글 번역문을 반환시키기 위해서
def genereateSubtitle(translateSubtitleReqDto:TranslateSubtitleReqDto) -> TranslateSubtitleResDto :    
    return TranslateSubtitleResDto(translateText(translateSubtitleReqDto.subtitle))
