# OpenAI API를 활용해서 오디오를 텍스트로 변환시키는 샘플 파일
# OpenAI의 ChatGPT를 활용해서 추가적인 작업을 수행하는 샘플 파일

from openai import OpenAI

class OpenAIProxy :
    def __init__(self) :
        self.__CLIENT = OpenAI()
    
    def transratedAudioText(self, autoFilePath:str) -> str :
        return self.__CLIENT.audio.transcriptions.create(
            model="whisper-1", 
            file=open(autoFilePath, "rb"),
            response_format="text"
        )

    def getChatGptAnswer(self, messages:list[str]) -> str :
        return self.__CLIENT.chat.completions.create(
            model="gpt-3.5-turbo",
            temperature=1.0,
            max_tokens=2000,
            messages=messages
        ).choices[0].message.content
    
    def getQnAForSentence(self, sentence:str) -> (str, str) :
        ANSWER = self.getChatGptAnswer([
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

    def getChatResponse(self, messages:str) -> str :
        chatHistory:list[dict] = []
        for index, messages in enumerate(messages) :
            chatHistory.append({
                "role": ("user" if (index%2 == 0) else "assistant"),
                "content": messages
            })
        return self.getChatGptAnswer(chatHistory)

openAIProxy:OpenAIProxy = OpenAIProxy()

# print(openAIProxy.transratedAudioText("./tmp/tmps/chunk_2.mp4"))

# print(openAIProxy.getQnAForSentence("to drop off my son, Donnie, for the Unicorn Student Exchange Program. Here's a list of his allergies.\n"))

# print(openAIProxy.getChatResponse([
#     "What does 'allergies' mean in this context?",
#     '\'Allergies\' refers to a person\'s negative physical reactions to certain substances or foods."',
#     "Give me some example sentenses."]))