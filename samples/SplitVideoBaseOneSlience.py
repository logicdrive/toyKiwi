# 특정 비디오의 오디오를 묵음 기간을 기준으로 분리시켜서 오디오들을 저장하기 위한 샘플 파일

from pydub import AudioSegment
from pydub.silence import split_on_silence
import os


if not os.path.exists("tmp/tmps"): 
    os.makedirs("tmp/tmps")


def splitVideosBaseOnSlience(targetFilePath:str, targetFileFormat:str, chunkDirPath:str, chunkFlieName:str,
                             cutStartMs:int=None, cutEndMs:int=None, 
                             minSilenceLen:int=250, silenceThresh:int=-50, keepSlience:bool=True) -> None:
    audioSegment = AudioSegment.from_file(targetFilePath, format=targetFileFormat)
    if cutStartMs and cutEndMs : audioSegment = audioSegment[cutStartMs:cutEndMs]
    elif cutStartMs : audioSegment = audioSegment[cutStartMs:]
    elif cutEndMs : audioSegment = audioSegment[:cutEndMs]

    audioChunks = split_on_silence(audioSegment, min_silence_len=minSilenceLen, silence_thresh=silenceThresh, keep_silence=keepSlience)
    for i, chunk in enumerate(audioChunks):
        chunk.export(f"{chunkDirPath}/{chunkFlieName}_{i}.mp4", format=targetFileFormat)

splitVideosBaseOnSlience("./tmp/temp.mp4", "mp4", "./tmp/tmps", "chunk", cutStartMs=1000*10, cutEndMs=1000*25)