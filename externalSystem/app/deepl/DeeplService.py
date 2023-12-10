from .reqDtos.TranslateSubtitleReqDto import TranslateSubtitleReqDto
from .resDtos.TranslateSubtitleResDto import TranslateSubtitleResDto

def genereateSubtitle(translateSubtitleReqDto:TranslateSubtitleReqDto) -> TranslateSubtitleResDto :
    if translateSubtitleReqDto.subtitle == "Test Subtitle No.1" :
        return TranslateSubtitleResDto("테스트 자막 No.1")

    if translateSubtitleReqDto.subtitle == "Test Subtitle No.2" :
        return TranslateSubtitleResDto("테스트 자막 No.2")
    
    return TranslateSubtitleResDto("테스트 자막")
