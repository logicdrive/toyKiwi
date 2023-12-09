package toykiwi.infra;

import javax.transaction.Transactional;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import toykiwi._global.config.kafka.KafkaProcessor;
import toykiwi._global.logger.CustomLogger;
import toykiwi._global.logger.CustomLoggerType;
import toykiwi.domain.ExternalSystemProxy;
import toykiwi.event.VideoUploadRequested;
import toykiwi.event.VideoUrlUploaded;
import toykiwi.event.GeneratedSubtitleUploaded;

@Service
@Transactional
public class PolicyHandler {
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
            ExternalSystemProxy.requestUploadingVideo(videoUploadRequested);
            CustomLogger.debug(CustomLoggerType.EXIT);

        } catch(Exception e) {
            CustomLogger.error(e, "", String.format("{videoUploadRequested: %s}", videoUploadRequested.toString()));
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
            ExternalSystemProxy.requestGeneratingSubtitle(videoUrlUploaded);
            CustomLogger.debug(CustomLoggerType.EXIT);

        } catch(Exception e) {
            CustomLogger.error(e, "", String.format("{videoUrlUploaded: %s}", videoUrlUploaded.toString()));
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
            ExternalSystemProxy.requestTranslatingSubtitle(generatingSubtitleUploaded);
            CustomLogger.debug(CustomLoggerType.EXIT);

        } catch(Exception e) {
            CustomLogger.error(e, "", String.format("{generatingSubtitleUploaded: %s}", generatingSubtitleUploaded.toString()));
        }
    }
}

