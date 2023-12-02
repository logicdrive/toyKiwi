package toykiwi.infra;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import toykiwi.config.kafka.KafkaProcessor;
import toykiwi.domain.*;

@Service
public class CollectedDataViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private CollectedDataRepository collectedDataRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenVideoUploadRequested_then_CREATE_1(
        @Payload VideoUploadRequested videoUploadRequested
    ) {
        try {
            if (!videoUploadRequested.validate()) return;

            // view 객체 생성
            CollectedData collectedData = new CollectedData();
            // view 객체에 이벤트의 Value 를 set 함
            collectedData.setVideoId(videoUploadRequested.getId());
            collectedData.setCuttedStartSecond(
                videoUploadRequested.getCuttedStartSecond()
            );
            collectedData.setCuttedEndSecond(
                videoUploadRequested.getCuttedEndSecond()
            );
            collectedData.setStatus("VideoUploadRequested");
            // view 레파지 토리에 save
            collectedDataRepository.save(collectedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenVideoUploadNotified_then_UPDATE_1(
        @Payload VideoUploadNotified videoUploadNotified
    ) {
        try {
            if (!videoUploadNotified.validate()) return;
            // view 객체 조회

            List<CollectedData> collectedDataList = collectedDataRepository.findByVideoId(
                videoUploadNotified.getId()
            );
            for (CollectedData collectedData : collectedDataList) {
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                collectedData.setUploadedUrl(
                    videoUploadNotified.getUploadedUrl()
                );
                collectedData.setStatus("VideoUploadNotified");
                // view 레파지 토리에 save
                collectedDataRepository.save(collectedData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenSubtitleUploadRequested_then_UPDATE_2(
        @Payload SubtitleUploadRequested subtitleUploadRequested
    ) {
        try {
            if (!subtitleUploadRequested.validate()) return;
            // view 객체 조회

            List<CollectedData> collectedDataList = collectedDataRepository.findByVideoId(
                subtitleUploadRequested.getVideoId()
            );
            for (CollectedData collectedData : collectedDataList) {
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                collectedData.setStatus("SubtitleUploadRequested");
                // view 레파지 토리에 save
                collectedDataRepository.save(collectedData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenGeneratedSubtitleUploaded_then_UPDATE_3(
        @Payload GeneratedSubtitleUploaded generatedSubtitleUploaded
    ) {
        try {
            if (!generatedSubtitleUploaded.validate()) return;
            // view 객체 조회

            List<CollectedData> collectedDataList = collectedDataRepository.findByVideoId(
                generatedSubtitleUploaded.getVideoId()
            );
            for (CollectedData collectedData : collectedDataList) {
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                collectedData.setSubtitles(
                    generatedSubtitleUploaded.getSubtitle()
                );
                collectedData.setStatus("GeneratedSubtitleUploaded");
                // view 레파지 토리에 save
                collectedDataRepository.save(collectedData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenTranlatedSubtitleUploaded_then_UPDATE_4(
        @Payload TranlatedSubtitleUploaded tranlatedSubtitleUploaded
    ) {
        try {
            if (!tranlatedSubtitleUploaded.validate()) return;
            // view 객체 조회

            List<CollectedData> collectedDataList = collectedDataRepository.findByVideoId(
                tranlatedSubtitleUploaded.getVideoId()
            );
            for (CollectedData collectedData : collectedDataList) {
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                collectedData.setSubtitles(
                    tranlatedSubtitleUploaded.getSubtitle()
                );
                collectedData.setStatus("TranslatedSubtitleUploaded");
                // view 레파지 토리에 save
                collectedDataRepository.save(collectedData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}
