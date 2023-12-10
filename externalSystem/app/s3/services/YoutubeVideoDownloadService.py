import pytube
import wget
from dataclasses import dataclass

from ..._global.logger import CustomLogger
from ..._global.logger import CustomLoggerType

@dataclass
class VideoMetadataDto:
    url: str
    title: str
    thumbnailUrl: str

    def __str__(self) :
        return "<VideoMetadataDto url: {}, title: {}, thumbnailUrl: {}>".format(self.url, self.title, self.thumbnailUrl)

# 주어진 URL로부터 데이터를 다운받고, 관련 메타데이터를 반환하기 위해서
def downloadYoutubeVideo(url:str, outputFilePath:str, outputFileName:str, thumbnailFileName:str) -> VideoMetadataDto :
    CustomLogger.debug(CustomLoggerType.EFFECT, "Try to download youtube video", "<url: {}, outputFilePath: {}, outputFileName: {}>".format(url, outputFilePath, outputFileName))
    youtube = pytube.YouTube(url)
    youtube.streams.get_highest_resolution()\
        .download(output_path=outputFilePath, filename=outputFileName)
    
    CustomLogger.debug(CustomLoggerType.EFFECT, "Try to download thumbnail of video", "<url: {}, outputPath: {}>".format(youtube.thumbnail_url, outputFilePath+thumbnailFileName))
    wget.download(youtube.thumbnail_url, outputFilePath+thumbnailFileName)
    videoMetadataDto:VideoMetadataDto = VideoMetadataDto(url, youtube.title, youtube.thumbnail_url)
    
    CustomLogger.debug(CustomLoggerType.EFFECT, "youtube Video and thumbnail was Downloaded", "<videoMetadataDto: {}>".format(videoMetadataDto))
    return videoMetadataDto