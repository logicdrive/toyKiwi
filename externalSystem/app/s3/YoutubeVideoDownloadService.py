from .._global.logger import CustomLogger
from .._global.logger import CustomLoggerType

import pytube
from dataclasses import dataclass

@dataclass
class VideoMetadataDto:
    url: str
    title: str
    thumbnailUrl: str

    def __str__(self) :
        return "<VideoMetadataDto url: {}, title: {}, thumbnailUrl: {}>".format(self.url, self.title, self.thumbnailUrl)

# 주어진 URL로부터 데이터를 다운받고, 관련 메타데이터를 반환하기 위해서
def downloadYoutubeVideo(url:str, outputFilePath:str, outputFileName:str) -> VideoMetadataDto :
    CustomLogger.debug(CustomLoggerType.EFFECT, "Try to download youtube video", "<url: {}, outputFilePath: {}, outputFileName: {}>".format(url, outputFilePath, outputFileName))

    youtube = pytube.YouTube(url)
    youtube.streams.get_highest_resolution()\
        .download(output_path=outputFilePath, filename=outputFileName)
    videoMetadataDto:VideoMetadataDto = VideoMetadataDto(url, youtube.title, youtube.thumbnail_url)
    
    CustomLogger.debug(CustomLoggerType.EFFECT, "youtube Video was Downloaded", "<videoMetadataDto: {}>".format(videoMetadataDto))
    return videoMetadataDto