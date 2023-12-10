package toykiwi.domain;

import toykiwi._global.config.kafka.KafkaProcessor;
import toykiwi._global.event.GeneratingSubtitleCompleted;
import toykiwi._global.event.TranslatingSubtitleCompleted;
import toykiwi._global.event.VideoRemoveRequested;
import toykiwi._global.logger.CustomLogger;
import toykiwi._global.logger.CustomLoggerType;

import javax.transaction.Transactional;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PolicyHandler {
    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    // 자막이 생성되었을 경우, 생성된 자막 관련 정보를 새로 추가시키기 위해서
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='GeneratingSubtitleCompleted'"
    )
    public void wheneverGeneratingSubtitleCompleted_uploadGeneratedSubtitle(
        @Payload GeneratingSubtitleCompleted generatingSubtitleCompleted
    ) {
        try
        {

            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{generatingSubtitleCompleted: %s}", generatingSubtitleCompleted.toString()));
            Subtitle.uploadGeneratedSubtitle(generatingSubtitleCompleted);
            CustomLogger.debug(CustomLoggerType.EXIT);

        } catch(Exception e) {
            CustomLogger.error(e, "", String.format("{generatingSubtitleCompleted: %s}", generatingSubtitleCompleted.toString()));
        }
    }

    // 자막에 대한 번역문이 생성되었을 경우, 해당 번역문을 업데이트시키기 위해서
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='TranslatingSubtitleCompleted'"
    )
    public void wheneverGeneratingSubtitleCompleted_uploadGeneratedSubtitle(
        @Payload TranslatingSubtitleCompleted translatingSubtitleCompleted
    ) {
        try
        {

            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{translatingSubtitleCompleted: %s}", translatingSubtitleCompleted.toString()));
            Subtitle.uploadTranslatedSubtitle(translatingSubtitleCompleted);
            CustomLogger.debug(CustomLoggerType.EXIT);

        } catch(Exception e) {
            CustomLogger.error(e, "", String.format("{translatingSubtitleCompleted: %s}", translatingSubtitleCompleted.toString()));
        }
    }

    // 비디오 삭제 요청이 있을 경우, 관련된 자막들을 전부 삭제시키기 위해서
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='VideoRemoveRequested'"
    )
    public void wheneverVideoRemoveRequestedd_removeSubtitles(
        @Payload VideoRemoveRequested videoRemoveRequested
    ) {
        try
        {

            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{videoRemoveRequested: %s}", videoRemoveRequested.toString()));
            Subtitle.removeSubtitles(videoRemoveRequested);
            CustomLogger.debug(CustomLoggerType.EXIT);

        } catch(Exception e) {
            CustomLogger.error(e, "", String.format("{videoRemoveRequested: %s}", videoRemoveRequested.toString()));
        }
    }
}
