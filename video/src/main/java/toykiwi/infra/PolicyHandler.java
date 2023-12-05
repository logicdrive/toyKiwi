package toykiwi.infra;

import javax.transaction.Transactional;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import toykiwi.domain.Video;
import toykiwi.config.kafka.KafkaProcessor;
import toykiwi.event.GeneratingSubtitleStarted;
import toykiwi.event.UploadingVideoCompleted;
import toykiwi.logger.CustomLogger;
import toykiwi.logger.CustomLoggerType;

@Service
@Transactional
public class PolicyHandler {
    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    // 비디오 URL 업로드가 완료되었을 경우, 업로드된 URL을 반영시키기 위해서
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='UploadingVideoCompleted'"
    )
    public void wheneverUploadingVideoCompleted_UploadVideoUrl(
        @Payload UploadingVideoCompleted uploadingVideoCompleted
    ) {
        try
        {

            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{uploadingVideoCompleted: %s}", uploadingVideoCompleted.toString()));
            Video.uploadVideoUrl(uploadingVideoCompleted);
            CustomLogger.debug(CustomLoggerType.EXIT);

        } catch(Exception e) {
            CustomLogger.error(e, "", String.format("{uploadingVideoCompleted: %s}", uploadingVideoCompleted.toString()));
        }
    }

    // 자막 생성이 시작되었을 경우, 관련 메타데이터들을 업데이트하기 위해서
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='GeneratingSubtitleStarted'"
    )
    public void wheneverGeneratingSubtitleStarted_UploadSubtitleMetadata(
        @Payload GeneratingSubtitleStarted generatingSubtitleStarted
    ) {
        try
        {

            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{generatingSubtitleStarted: %s}", generatingSubtitleStarted.toString()));
            Video.uploadSubtitleMetadata(generatingSubtitleStarted);
            CustomLogger.debug(CustomLoggerType.EXIT);

        } catch(Exception e) {
            CustomLogger.error(e, "", String.format("{generatingSubtitleStarted: %s}", generatingSubtitleStarted.toString()));
        }
    }
}
