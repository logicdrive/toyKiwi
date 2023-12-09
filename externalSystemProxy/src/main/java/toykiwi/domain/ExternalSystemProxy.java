package toykiwi.domain;

import toykiwi._global.event.GeneratedSubtitleUploaded;
import toykiwi._global.event.GeneratingSubtitleCompleted;
import toykiwi._global.event.GeneratingSubtitleStarted;
import toykiwi._global.event.TranslatingSubtitleCompleted;
import toykiwi._global.event.UploadingVideoCompleted;
import toykiwi._global.event.VideoUploadRequested;
import toykiwi._global.event.VideoUrlUploaded;
import toykiwi._global.logger.CustomLogger;
import toykiwi._global.logger.CustomLoggerType;

public class ExternalSystemProxy {
    // 비디오 업로드 요청관련 이벤트 발생시 비디오를 업로드하고, 관련 정보를 이벤트로 전달하기 위해서
    public static void requestUploadingVideo(VideoUploadRequested videoUploadRequested) {
        
        if(videoUploadRequested.getId() == 1)
        {
            CustomLogger.debug(CustomLoggerType.EFFECT, "MOCK: Get URL and title from ExternalSystem", String.format("{videoUploadRequested: %s}", videoUploadRequested.toString()));

            UploadingVideoCompleted uploadingVideoCompleted = new UploadingVideoCompleted();
            uploadingVideoCompleted.setVideoId(1L);
            uploadingVideoCompleted.setVideoTitle("Test Video Title");
            uploadingVideoCompleted.setUploadedUrl("https://s3.testurl.mp4");
            uploadingVideoCompleted.setThumbnailUrl("https://test.thumbnailUrl.jpg");
            uploadingVideoCompleted.publishAfterCommit();
            return;
        }


        UploadingVideoCompleted uploadingVideoCompleted = new UploadingVideoCompleted();
        uploadingVideoCompleted.publishAfterCommit();
    
    }

    // 비디오 URL 업데이트 완료시, 해당 URL에 접속해서 자막을 추출하고, 관련 정보를 이벤트로 전달하기 위해서
    public static void requestGeneratingSubtitle(VideoUrlUploaded videoUrlUploaded) {

        if(videoUrlUploaded.getId() == 1)
        {
            CustomLogger.debug(CustomLoggerType.EFFECT, "MOCK: Generating subtitle from ExternalSystem", String.format("{videoUrlUploaded: %s}", videoUrlUploaded.toString()));

            GeneratingSubtitleStarted generatingSubtitleStarted = new GeneratingSubtitleStarted();
            generatingSubtitleStarted.setVideoId(1L);
            generatingSubtitleStarted.setSubtitleCount(2);
            generatingSubtitleStarted.publishAfterCommit();

            GeneratingSubtitleCompleted generatingSubtitleCompleted = new GeneratingSubtitleCompleted();
            generatingSubtitleCompleted.setVideoId(1L);
            generatingSubtitleCompleted.setSubtitle("Test Subtitle No.1");
            generatingSubtitleCompleted.setStartSecond(0);
            generatingSubtitleCompleted.setEndSecond(5);
            generatingSubtitleCompleted.publishAfterCommit();

            GeneratingSubtitleCompleted generatingSubtitleCompleted_2 = new GeneratingSubtitleCompleted();
            generatingSubtitleCompleted_2.setVideoId(1L);
            generatingSubtitleCompleted_2.setSubtitle("Test Subtitle No.2");
            generatingSubtitleCompleted_2.setStartSecond(5);
            generatingSubtitleCompleted_2.setEndSecond(10);
            generatingSubtitleCompleted_2.publishAfterCommit();
            return;
        }

        GeneratingSubtitleCompleted generatingSubtitleCompleted = new GeneratingSubtitleCompleted();
        generatingSubtitleCompleted.publishAfterCommit();
    
    }

    // 비디오 자막 업데이트시, 해당 자막에 대한 번역문을 생성하고, 관련 정보를 이벤트로 전달하기 위해서
    public static void requestTranslatingSubtitle(GeneratedSubtitleUploaded generatingSubtitleUploaded) {

        if(generatingSubtitleUploaded.getId() == 1)
        {
            CustomLogger.debug(CustomLoggerType.EFFECT, "MOCK: Get translatedSubtitle and from ExternalSystem", String.format("{generatingSubtitleUploaded: %s}", generatingSubtitleUploaded.toString()));
        
            TranslatingSubtitleCompleted translatingSubtitleCompleted = new TranslatingSubtitleCompleted();
            translatingSubtitleCompleted.setSubtitleId(1L);
            translatingSubtitleCompleted.setTranslatedSubtitle("테스트 자막 No.1");
            translatingSubtitleCompleted.publishAfterCommit();
            return;
        }

        if(generatingSubtitleUploaded.getId() == 2)
        {
            CustomLogger.debug(CustomLoggerType.EFFECT, "MOCK: Get translatedSubtitle and from ExternalSystem", String.format("{generatingSubtitleUploaded: %s}", generatingSubtitleUploaded.toString()));
        
            TranslatingSubtitleCompleted translatingSubtitleCompleted = new TranslatingSubtitleCompleted();
            translatingSubtitleCompleted.setSubtitleId(2L);
            translatingSubtitleCompleted.setTranslatedSubtitle("테스트 자막 No.2");
            translatingSubtitleCompleted.publishAfterCommit();
            return;        
        }

        TranslatingSubtitleCompleted translatingSubtitleCompleted = new TranslatingSubtitleCompleted();
        translatingSubtitleCompleted.publishAfterCommit();
    
    }
}
