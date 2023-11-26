# DeepL을 이용해서 번역을 수행하는 샘플파일

import os
import deepl

class DeepLProxy :
    def __init__(self) :
        self.__CLIENT = deepl.Translator(os.environ["DEEPL_API_KEY"])
    
    def translatedText(self, text:str, targetLang:str="KO") -> str :
        return self.__CLIENT.translate_text(text, target_lang=targetLang).text

deepLProxy:DeepLProxy = DeepLProxy()
print(deepLProxy.translatedText("Hello, world!"))