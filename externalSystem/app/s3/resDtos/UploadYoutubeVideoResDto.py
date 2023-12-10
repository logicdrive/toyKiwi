from flask import jsonify

class UploadYoutubeVideoResDto:
    def __init__(self, videoTitle:str="", uploadedUrl:str="", thumbnailUrl:str="") :
        self.__videoTitle:str = videoTitle
        self.__uploadedUrl:str = uploadedUrl
        self.__thumbnailUrl:str = thumbnailUrl
    

    def __str__(self) :
        return "<UploadYoutubeVideoResDto videoTitle: {}, uploadedUrl: {}, thumbnailUrl: {}>"\
                    .format(self.__videoTitle, self.__uploadedUrl, self.__thumbnailUrl)


    @property
    def videoTitle(self) -> str :
        return self.__videoTitle
    
    @property
    def uploadedUrl(self) -> str :
        return self.__uploadedUrl
    
    @property
    def thumbnailUrl(self) -> str :
        return self.__thumbnailUrl
    

    @videoTitle.setter
    def videoTitle(self, videoTitle) :
        self.__videoTitle = videoTitle

    @uploadedUrl.setter
    def uploadedUrl(self, uploadedUrl) :
        self.__uploadedUrl = uploadedUrl

    @thumbnailUrl.setter
    def thumbnailUrl(self, thumbnailUrl) :
        self.__thumbnailUrl = thumbnailUrl
    

    def json(self) -> str :
        return jsonify({
            "videoTitle": self.__videoTitle,
            "uploadedUrl": self.__uploadedUrl,
            "thumbnailUrl": self.__thumbnailUrl
        })
