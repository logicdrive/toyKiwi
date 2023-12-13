package toykiwi.domain;

import toykiwi._global.config.kafka.KafkaProcessor;
import toykiwi._global.event.GeneratedSubtitleUploaded;
import toykiwi._global.event.TranlatedSubtitleUploaded;
import toykiwi._global.event.VideoRemoveRequested;
import toykiwi._global.event.VideoRemovedByFail;
import toykiwi._global.event.VideoUploadFailed;
import toykiwi._global.event.VideoUploadRequested;
import toykiwi._global.event.VideoUrlUploaded;
import toykiwi._global.logger.CustomLogger;
import toykiwi._global.logger.CustomLoggerType;

import javax.transaction.Transactional;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PolicyHandler {
    private final ExternalSystemProxy externalSystemProxy;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    // 비디오 업로드 요청관련 이벤트 발생시 비디오를 업로드하고, 관련 정보를 이벤트로 전달하기 위해서
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='VideoUploadRequested'"
    )
    public void wheneverVideoUploadRequested_RequestUploadingVideo(
        @Payload VideoUploadRequested videoUploadRequested
    ) {
        try
        {

            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{videoUploadRequested: %s}", videoUploadRequested.toString()));
            this.externalSystemProxy.requestUploadingVideo(videoUploadRequested);
            CustomLogger.debug(CustomLoggerType.EXIT);

        } catch(Exception e) {
            CustomLogger.error(e, "", String.format("{videoUploadRequested: %s}", videoUploadRequested.toString()));
        
            VideoUploadFailed videoUploadFailed = new VideoUploadFailed(videoUploadRequested.getId());
            videoUploadFailed.publishAfterCommit();
        }
    }

    // 비디오 URL 업데이트 완료시, 해당 URL에 접속해서 자막을 추출하고, 관련 정보를 이벤트로 전달하기 위해서
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='VideoUrlUploaded'"
    )
    public void wheneverVideoUrlUploaded_RequestGeneratingSubtitle(
        @Payload VideoUrlUploaded videoUrlUploaded
    ) {
        try
        {

            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{videoUrlUploaded: %s}", videoUrlUploaded.toString()));
            this.externalSystemProxy.requestGeneratingSubtitle(videoUrlUploaded);
            CustomLogger.debug(CustomLoggerType.EXIT);

        } catch(Exception e) {
            CustomLogger.error(e, "", String.format("{videoUrlUploaded: %s}", videoUrlUploaded.toString()));
        
            VideoUploadFailed videoUploadFailed = new VideoUploadFailed(videoUrlUploaded.getId());
            videoUploadFailed.publishAfterCommit();
        }
    }

    // 비디오 자막 업데이트시, 해당 자막에 대한 번역문을 생성하고, 관련 정보를 이벤트로 전달하기 위해서
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='GeneratedSubtitleUploaded'"
    )
    public void wheneverGeneratedSubtitleUploaded_RequestTranslatingSubtitle(
        @Payload GeneratedSubtitleUploaded generatingSubtitleUploaded
    ) {
        try
        {

            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{generatingSubtitleUploaded: %s}", generatingSubtitleUploaded.toString()));
            this.externalSystemProxy.requestTranslatingSubtitle(generatingSubtitleUploaded);
            CustomLogger.debug(CustomLoggerType.EXIT);

        } catch(Exception e) {
            CustomLogger.error(e, "", String.format("{generatingSubtitleUploaded: %s}", generatingSubtitleUploaded.toString()));
        
            VideoUploadFailed videoUploadFailed = new VideoUploadFailed(generatingSubtitleUploaded.getVideoId());
            videoUploadFailed.publishAfterCommit();
        }
    }

   // 번역문 업데이트시, 자막에 대한 질문 및 응답 생성 요청을 수행하기 위해서
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='TranlatedSubtitleUploaded'"
    )
    public void wheneverTranlatedSubtitleUploaded_RequestGeneratingQnA(
        @Payload TranlatedSubtitleUploaded tranlatedSubtitleUploaded
    ) {
        try
        {

            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{tranlatedSubtitleUploaded: %s}", tranlatedSubtitleUploaded.toString()));
            this.externalSystemProxy.requestGeneratingQnA(tranlatedSubtitleUploaded);
            CustomLogger.debug(CustomLoggerType.EXIT);

        } catch(Exception e) {
            CustomLogger.error(e, "", String.format("{tranlatedSubtitleUploaded: %s}", tranlatedSubtitleUploaded.toString()));
        
            VideoUploadFailed videoUploadFailed = new VideoUploadFailed(tranlatedSubtitleUploaded.getVideoId());
            videoUploadFailed.publishAfterCommit();
        }
    }


    // 비디오 삭제 요청시 S3에 업로드된 관련된 비디오 및 썸네일을 삭제시키기 위해서
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='VideoRemoveRequested'"
    )
    public void wheneverVideoRemoveRequested_RequestRemovingVideo(
        @Payload VideoRemoveRequested videoRemoveRequested
    ) {
        try
        {

            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{videoRemoveRequested: %s}", videoRemoveRequested.toString()));
            this.externalSystemProxy.requestRemovingVideo(videoRemoveRequested);
            CustomLogger.debug(CustomLoggerType.EXIT);

        } catch(Exception e) {
            CustomLogger.error(e, "", String.format("{videoRemoveRequested: %s}", videoRemoveRequested.toString()));
        }
    }

    // 비디오 업로드 실패시 S3에 업로드된 관련된 비디오 및 썸네일을 삭제시키기 위해서
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='VideoRemovedByFail'"
    )
    public void wheneverVideoRemovedByFail_RequestRemovingVideoByFail(
        @Payload VideoRemovedByFail videoRemovedByFail
    ) {
        try
        {

            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{videoRemovedByFail: %s}", videoRemovedByFail.toString()));
            this.externalSystemProxy.requestRemovingVideoByFail(videoRemovedByFail);
            CustomLogger.debug(CustomLoggerType.EXIT);

        } catch(Exception e) {
            CustomLogger.error(e, "", String.format("{videoRemovedByFail: %s}", videoRemovedByFail.toString()));
        }
    }
}

