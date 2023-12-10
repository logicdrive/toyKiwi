import os
import deepl

from ..._global.logger import CustomLogger
from ..._global.logger import CustomLoggerType

CLIENT = deepl.Translator(os.environ["DEEPL_API_KEY"])

# 주어진 텍스트에 대한 번역문을 반환시키기 위해서
def translateText(text:str, targetLang:str="KO") -> str :
    CustomLogger.debug(CustomLoggerType.EFFECT, "Try to translate given text", "<text: {}, targetLang: {}>".format(text, targetLang))
    translatedText = CLIENT.translate_text(text, target_lang=targetLang).text
    CustomLogger.debug(CustomLoggerType.EFFECT, "Traslating completed", "<translatedText: {}>".format(translatedText))
    return translatedText
