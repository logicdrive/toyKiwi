# 특정 유튜브 URL로부터 다운로드를 받는 샘플 파일

import pytube
from dataclasses import dataclass


@dataclass
class VideoMetadataDto:
    url: str
    title: str
    thumbnailUrl: str

def downloadYoutubeVideo(url:str, outputFilePath:str, outputFileName:str) -> VideoMetadataDto :
    youtube = pytube.YouTube(url)
    youtube.streams.get_highest_resolution()\
        .download(output_path=outputFilePath, filename=outputFileName)
    return VideoMetadataDto(url, youtube.title, youtube.thumbnail_url)


videoMetadataDto:VideoMetadataDto = downloadYoutubeVideo("https://www.youtube.com/watch?v=fkvhqBMmnCg", "./tmp", "temp.mp4")

print(videoMetadataDto.url)
print(videoMetadataDto.title)
print(videoMetadataDto.thumbnailUrl)