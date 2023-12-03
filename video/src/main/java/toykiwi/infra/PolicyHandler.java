package toykiwi.infra;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import toykiwi.config.kafka.KafkaProcessor;
import toykiwi.domain.*;
import toykiwi.event.UploadingVideoCompleted;
import toykiwi.logger.CustomLogger;
import toykiwi.logger.CustomLoggerType;

@Service
@Transactional
public class PolicyHandler {
    @Autowired
    VideoRepository videoRepository;

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
        CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{UploadingVideoCompleted: %s}", uploadingVideoCompleted.toString()));
        Video.uploadVideoUrl(uploadingVideoCompleted);
        CustomLogger.debug(CustomLoggerType.EXIT);
    }
}
