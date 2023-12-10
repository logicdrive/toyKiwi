import os
import pytube
import wget
from dataclasses import dataclass
from moviepy.video.io.ffmpeg_tools import ffmpeg_extract_subclip

from ..._global.logger import CustomLogger
from ..._global.logger import CustomLoggerType

@dataclass
class VideoMetadataDto:
    url: str
    title: str
    thumbnailUrl: str
    outputVideoPath: str
    outputThumbnailPath: str

    def __str__(self) :
        return "<VideoMetadataDto url: {}, title: {}, thumbnailUrl: {}>".format(self.url, self.title, self.thumbnailUrl)

# 주어진 URL로부터 비디오를 다운받고, 관련 메타데이터를 반환하기 위해서
def downloadYoutubeVideo(url:str, outputFilePath:str, outputFileName:str, thumbnailFileName:str) -> VideoMetadataDto :
    outputVideoPath:str = outputFilePath + outputFileName
    outputThumbnailPath:str = outputFilePath + thumbnailFileName

    CustomLogger.debug(CustomLoggerType.EFFECT, "Try to download youtube video", "<url: {}, outputVideoPath: {}>".format(url, outputVideoPath))
    youtube = pytube.YouTube(url)
    youtube.streams.get_highest_resolution()\
        .download(output_path=outputFilePath, filename=outputFileName)
    
    CustomLogger.debug(CustomLoggerType.EFFECT, "Try to download thumbnail of video", "<url: {}, outputThumbnailPath: {}>".format(youtube.thumbnail_url, outputThumbnailPath))
    wget.download(youtube.thumbnail_url, outputThumbnailPath)
    videoMetadataDto:VideoMetadataDto = VideoMetadataDto(url, youtube.title, youtube.thumbnail_url, outputVideoPath, outputThumbnailPath)
    
    CustomLogger.debug(CustomLoggerType.EFFECT, "youtube Video and thumbnail was Downloaded", "<videoMetadataDto: {}>".format(videoMetadataDto))
    return videoMetadataDto

# 주어진 URL로부터 비디오를 다운받고, 그 비디오를 자른 뒤에, 관련 메타데이터를 반환하기 위해서
def downloadCuttedYoutubeVideo(url:str, outputFilePath:str, outputFileName:str, thumbnailFileName:str, cuttedStartSecond:int, cuttedEndSecond:int) -> VideoMetadataDto :
    videoMetadataDto:VideoMetadataDto = downloadYoutubeVideo(url, outputFilePath, outputFileName, thumbnailFileName)

    CustomLogger.debug(CustomLoggerType.EFFECT, "Try to cut downloaded video", "<videoMetadataDto: {}, cuttedStartSecond: {}, cuttedEndSecond: {}>".format(videoMetadataDto, cuttedStartSecond, cuttedEndSecond))
    cuttedVideoPath:str = outputFilePath + "cuttedVideo.mp4"
    ffmpeg_extract_subclip(videoMetadataDto.outputVideoPath, cuttedStartSecond, cuttedEndSecond, targetname=cuttedVideoPath)
    CustomLogger.debug(CustomLoggerType.EFFECT, "Cutting video completed")

    CustomLogger.debug(CustomLoggerType.EFFECT, "Try to replace video to cuttedVideo")
    os.remove(videoMetadataDto.outputVideoPath)
    os.rename(cuttedVideoPath, videoMetadataDto.outputVideoPath)
    CustomLogger.debug(CustomLoggerType.EFFECT, "Replace processing completed")

    return videoMetadataDto