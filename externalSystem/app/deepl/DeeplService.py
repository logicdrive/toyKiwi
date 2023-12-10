from .reqDtos.TranslateSubtitleReqDto import TranslateSubtitleReqDto
from .resDtos.TranslateSubtitleResDto import TranslateSubtitleResDto

# 주어진 자막에 대한 한글 번역문을 반환시키기 위해서
def genereateSubtitle(translateSubtitleReqDto:TranslateSubtitleReqDto) -> TranslateSubtitleResDto :
    if translateSubtitleReqDto.subtitle == "Test Subtitle No.1" :
        return TranslateSubtitleResDto("테스트 자막 No.1")

    if translateSubtitleReqDto.subtitle == "Test Subtitle No.2" :
        return TranslateSubtitleResDto("테스트 자막 No.2")
    
    return TranslateSubtitleResDto("테스트 자막")
