# 특정 유튜브 URL로부터 다운로드를 받는 샘플 파일

import pytube

def downloadYoutubeVideo(url:str, outputFilePath:str, outputFileName:str) -> None :
    pytube.YouTube(url)\
        .streams.get_highest_resolution()\
        .download(output_path=outputFilePath, filename=outputFileName)

downloadYoutubeVideo("https://www.youtube.com/watch?v=fkvhqBMmnCg", "./tmp", "temp.mp4")