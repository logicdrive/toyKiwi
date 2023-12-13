from openai import OpenAI

from ..._global.logger import CustomLogger
from ..._global.logger import CustomLoggerType

CLIENT = OpenAI()

# 주어진 오디오 파일에서 자막을 생성시키기 위해서
def generateAudioText(autoFilePath:str) -> str :
    CustomLogger.debug(CustomLoggerType.EFFECT, "Try to generate audio text", "<autoFilePath: {}>".format(autoFilePath))
    
    audioFile = open(autoFilePath, "rb")
    audioText = CLIENT.audio.transcriptions.create(
        model="whisper-1", 
        file=audioFile,
        response_format="text"
    )
    audioFile.close()
    
    CustomLogger.debug(CustomLoggerType.EFFECT, "Audio text generated", "<audioText: {}>".format(audioText))
    return audioText


# ChatGPT와 통신해서 관련 응답을 얻어내기 위해서
def getChatGptAnswer(messages:list[str]) -> str :
    CustomLogger.debug(CustomLoggerType.EFFECT, "Try to get reponse by using ChatGPT", "<messages: {}>".format(messages))

    answer:str = CLIENT.chat.completions.create(
                model="gpt-3.5-turbo",
                temperature=1.0,
                max_tokens=2000,
                messages=messages
            ).choices[0].message.content
    
    CustomLogger.debug(CustomLoggerType.EFFECT, "Answer was acquired from ChatGPT", "<answer: {}>".format(answer))
    return answer

# CharGPT로부터 특정 문장에 대해 질문 및 응답 쌍을 생성시키기 위해서
def getQnAForSentenceByUsingChatGPT(sentence:str) -> (str, str) :
    ANSWER = getChatGptAnswer([
        { "role": "system", "content": 
"""You are an English teacher for Korean students.
When I suggest some sentences, You should pick a word that seems difficult for students and make a question and answer for that word.
Please follow the below template.
\"\"\"
Q: [Question]
A: [Answer]
\"\"\"
You should make only one question and answer.
And, please wrap with double quotes to the word you want to explain in your question and answer."""
        },
        {"role": "user", "content": sentence},
    ])

    Q_INDEX, A_INDEX = ANSWER.find("Q:"), ANSWER.find("A:")
    return (ANSWER[Q_INDEX+3:A_INDEX-1], ANSWER[A_INDEX+3:])