package toykiwi.infra;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.naming.NameParser;
import javax.naming.NameParser;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import toykiwi.config.kafka.KafkaProcessor;
import toykiwi.domain.*;

//<<< Clean Arch / Inbound Adaptor
@Service
@Transactional
public class PolicyHandler {

    @Autowired
    SubtitleRepository subtitleRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='VideoUploadNotified'"
    )
    public void wheneverVideoUploadNotified_RequestSubtitleUpload(
        @Payload VideoUploadNotified videoUploadNotified
    ) {
        VideoUploadNotified event = videoUploadNotified;
        System.out.println(
            "\n\n##### listener RequestSubtitleUpload : " +
            videoUploadNotified +
            "\n\n"
        );

        // Sample Logic //
        Subtitle.requestSubtitleUpload(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
