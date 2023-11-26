# 특정 비디오의 오디오를 묵음 기간을 기준으로 분리시켜서 오디오들을 저장하기 위해서
from pydub import AudioSegment
from pydub.silence import split_on_silence
import os


if not os.path.exists("tmp/tmps"): 
    os.makedirs("tmp/tmps")


def splitVideosBaseOnSlience(targetFilePath:str, targetFileFormat:str, chunkDirPath:str, chunkFlieName:str, 
                             minSilenceLen:int=250, silenceThresh:int=-50, keepSlience:bool=True) -> None:
    audioSegment = AudioSegment.from_file(targetFilePath, format=targetFileFormat)
    audioChunks = split_on_silence(audioSegment, min_silence_len=minSilenceLen, silence_thresh=silenceThresh, keep_silence=keepSlience)
    for i, chunk in enumerate(audioChunks):
        chunk.export(f"{chunkDirPath}/{chunkFlieName}_{i}.mp4", format=targetFileFormat)

splitVideosBaseOnSlience("./tmp/temp.mp4", "mp4", "./tmp/tmps", "chunk")