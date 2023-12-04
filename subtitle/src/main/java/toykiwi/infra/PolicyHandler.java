package toykiwi.infra;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import toykiwi.config.kafka.KafkaProcessor;
import toykiwi.domain.Subtitle;
import toykiwi.domain.SubtitleRepository;
import toykiwi.event.GeneratingSubtitleCompleted;
import toykiwi.event.TranslatingSubtitleCompleted;
import toykiwi.logger.CustomLogger;
import toykiwi.logger.CustomLoggerType;

@Service
@Transactional
public class PolicyHandler {
    @Autowired
    SubtitleRepository subtitleRepository;

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
}
