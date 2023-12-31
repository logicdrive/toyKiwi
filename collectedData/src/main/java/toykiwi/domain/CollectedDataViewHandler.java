package toykiwi.domain;

import toykiwi._global.config.kafka.KafkaProcessor;
import toykiwi._global.event.GeneratedQnAUploaded;
import toykiwi._global.event.GeneratedSubtitleUploaded;
import toykiwi._global.event.SubtitleMetadataUploaded;
import toykiwi._global.event.TranlatedSubtitleUploaded;
import toykiwi._global.event.VideoRemoveRequested;
import toykiwi._global.event.VideoUploadFailed;
import toykiwi._global.event.VideoUploadRequested;
import toykiwi._global.event.VideoUrlUploaded;
import toykiwi._global.exceptions.InvalidSubtitleIdException;
import toykiwi._global.exceptions.InvalidVideoIdException;
import toykiwi._global.logger.CustomLogger;
import toykiwi._global.logger.CustomLoggerType;
import toykiwi.webSocket.videoSubscribe.VideoSubscribeSocketHandler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class CollectedDataViewHandler {
    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private SubtitleRepository subtitleRepository;

    @Autowired
    private VideoSubscribeSocketHandler videoSubscribeSocketHandler;


    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='VideoUploadRequested'"
    )
    public void whenVideoUploadRequested_then_CREATE_1(
        @Payload VideoUploadRequested videoUploadRequested
    ) {
        try {

            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{videoUploadRequested: %s}", videoUploadRequested.toString()));
            if (!videoUploadRequested.validate()) return;

            Video videoToCreate = new Video();
            videoToCreate.setVideoId(videoUploadRequested.getId());
            videoToCreate.setYoutubeUrl(videoUploadRequested.getYoutubeUrl());
            videoToCreate.setCuttedStartSecond(videoUploadRequested.getCuttedStartSecond());
            videoToCreate.setCuttedEndSecond(videoUploadRequested.getCuttedEndSecond());
            videoToCreate.setStatus("VideoUploadRequested");
            Video savedVideo = this.videoRepository.save(videoToCreate);


            videoSubscribeSocketHandler.notifyVideoUpdate(savedVideo);
            CustomLogger.debug(CustomLoggerType.EXIT, "", String.format("{videoToCreate: %s}", videoToCreate.toString()));

        } catch (Exception e) {
            CustomLogger.error(e, "", String.format("{videoUploadRequested: %s}", videoUploadRequested.toString()));
        }
    }



    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='VideoUrlUploaded'"
    )
    public void whenVideoUrlUploaded_then_UPDATE_1(
        @Payload VideoUrlUploaded videoUrlUploaded
    ) {
        try {

            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{videoUrlUploaded: %s}", videoUrlUploaded.toString()));
            if (!videoUrlUploaded.validate()) return;

            CustomLogger.debug(CustomLoggerType.EFFECT, "Try to search video", String.format("{videoId: %s}", videoUrlUploaded.getId()));
            List<Video> videos = this.videoRepository.findAllByVideoId(videoUrlUploaded.getId());
            if(videos.size() != 1)
                throw new InvalidVideoIdException();
            
            Video videoToUpdate = videos.get(0);
            videoToUpdate.setVideoTitle(videoUrlUploaded.getTitle());
            videoToUpdate.setUploadedUrl(videoUrlUploaded.getUploadedUrl());
            videoToUpdate.setThumbnailUrl(videoUrlUploaded.getThumbnailUrl());
            videoToUpdate.setStatus("VideoUrlUploaded");
            Video savedVideo = this.videoRepository.save(videoToUpdate);


            videoSubscribeSocketHandler.notifyVideoUpdate(savedVideo);
            CustomLogger.debug(CustomLoggerType.EXIT, "", String.format("{videoToUpdate: %s}", videoToUpdate.toString()));

        } catch (Exception e) {
            CustomLogger.error(e, "", String.format("{videoUrlUploaded: %s}", videoUrlUploaded.toString()));
        }
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='SubtitleMetadataUploaded'"
    )
    public void whenSubtitleMetadataUploaded_then_UPDATE_2(
        @Payload SubtitleMetadataUploaded subtitleMetadataUploaded
    ) {
        try {

            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{subtitleMetadataUploaded: %s}", subtitleMetadataUploaded.toString()));
            if (!subtitleMetadataUploaded.validate()) return;

            CustomLogger.debug(CustomLoggerType.EFFECT, "Try to search video", String.format("{videoId: %s}", subtitleMetadataUploaded.getId()));
            List<Video> videos = this.videoRepository.findAllByVideoId(subtitleMetadataUploaded.getId());
            if(videos.size() != 1)
                throw new InvalidVideoIdException();
            
            Video videoToUpdate = videos.get(0);
            videoToUpdate.setSubtitleCount(subtitleMetadataUploaded.getSubtitleCount());
            videoToUpdate.setStatus("SubtitleMetadataUploaded");
            Video savedVideo = this.videoRepository.save(videoToUpdate);


            videoSubscribeSocketHandler.notifyVideoUpdate(savedVideo);
            CustomLogger.debug(CustomLoggerType.EXIT, "", String.format("{videoToUpdate: %s}", videoToUpdate.toString()));

        } catch (Exception e) {
            CustomLogger.error(e, "", String.format("{subtitleMetadataUploaded: %s}", subtitleMetadataUploaded.toString()));
        }
    }
    

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='GeneratedSubtitleUploaded'"
    )
    public void whenGeneratedSubtitleUploaded_then_UPDATE_3(
        @Payload GeneratedSubtitleUploaded generatedSubtitleUploaded
    ) {
        try {

            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{generatedSubtitleUploaded: %s}", generatedSubtitleUploaded.toString()));
            if (!generatedSubtitleUploaded.validate()) return;

            List<Video> videos = this.videoRepository.findAllByVideoId(generatedSubtitleUploaded.getVideoId());
            if(videos.size() != 1)
                throw new InvalidVideoIdException();
            Video subtitleVideo = videos.get(0);


            // 해당 비디오에 대한 자막 정보를 새로 생성시키기 위해서
            Subtitle subtitleToCreate = new Subtitle();
            subtitleToCreate.setSubtitleId(generatedSubtitleUploaded.getId());
            subtitleToCreate.setSubtitle(generatedSubtitleUploaded.getSubtitle());
            subtitleToCreate.setStartSecond(generatedSubtitleUploaded.getStartSecond());
            subtitleToCreate.setEndSecond(generatedSubtitleUploaded.getEndSecond());
            subtitleToCreate.setVideo(subtitleVideo);
            this.subtitleRepository.save(subtitleToCreate);

            // 해당 비디오에 대한 모든 자막이 생성되었을 경우, 상태를 업데이트시키기 위해서
            List<Subtitle> subtitlesToCheck = this.subtitleRepository.findAllByVideo(subtitleVideo);
            if(subtitlesToCheck.size() == subtitleVideo.getSubtitleCount())
            {
                subtitleVideo.setStatus("GeneratedSubtitleUploaded");

                Video savedVideo = this.videoRepository.save(subtitleVideo);
                videoSubscribeSocketHandler.notifyVideoUpdate(savedVideo);
            }

            
            CustomLogger.debug(CustomLoggerType.EXIT, "", String.format("{subtitleToCreate: %s, subtitlesToCheckSize: %d}", subtitleToCreate.toString(), subtitlesToCheck.size()));

        } catch (Exception e) {
            CustomLogger.error(e, "", String.format("{generatedSubtitleUploaded: %s}", generatedSubtitleUploaded.toString()));
        }
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='TranlatedSubtitleUploaded'"
    )
    public void whenTranlatedSubtitleUploaded_then_UPDATE_4(
        @Payload TranlatedSubtitleUploaded tranlatedSubtitleUploaded
    ) {
        try {

            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{tranlatedSubtitleUploaded: %s}", tranlatedSubtitleUploaded.toString()));
            if (!tranlatedSubtitleUploaded.validate()) return;


            // 해당하는 자막에 번역문을 업데이트시키기 위해서
            List<Subtitle> subtitles = this.subtitleRepository.findAllBySubtitleId(tranlatedSubtitleUploaded.getId());
            if(subtitles.size() != 1)
                throw new InvalidSubtitleIdException();
            Subtitle subtitleToUpdate = subtitles.get(0);

            subtitleToUpdate.setTranslatedSubtitle(tranlatedSubtitleUploaded.getTranslatedSubtitle());
            this.subtitleRepository.save(subtitleToUpdate);


            // 모든 자막에 번역문이 업데이트되었을 경우, 상태를 업데이트시키기 위해서
            List<Video> videos = this.videoRepository.findAllByVideoId(tranlatedSubtitleUploaded.getVideoId());
            if(videos.size() != 1)
                throw new InvalidVideoIdException();
            Video subtitleVideo = videos.get(0);

            List<Subtitle> subtitlesToCheck = this.subtitleRepository.findAllByVideo(subtitleVideo);
            int translatedSubtitleCount = subtitlesToCheck.stream().filter((subtitle) -> {
                return !((subtitle.getTranslatedSubtitle()==null) || (subtitle.getTranslatedSubtitle().isEmpty()));
            }).toArray().length;

            if(translatedSubtitleCount == subtitleVideo.getSubtitleCount())
            {
                subtitleVideo.setStatus("TranlatedSubtitleUploaded");

                Video savedVideo = this.videoRepository.save(subtitleVideo);
                videoSubscribeSocketHandler.notifyVideoUpdate(savedVideo);
            }


            CustomLogger.debug(CustomLoggerType.EXIT, "", String.format("{subtitleToUpdate: %s, translatedSubtitleCount: %d}", subtitleToUpdate.toString(), translatedSubtitleCount));

        } catch (Exception e) {
            CustomLogger.error(e, "", String.format("{tranlatedSubtitleUploaded: %s}", tranlatedSubtitleUploaded.toString()));
        }
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='GeneratedQnAUploaded'"
    )
    public void whenGeneratedQnAUploaded_then_UPDATE_5(
        @Payload GeneratedQnAUploaded generatedQnAUploaded
    ) {
        try {

            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{generatedQnAUploaded: %s}", generatedQnAUploaded.toString()));
            if (!generatedQnAUploaded.validate()) return;


            // 해당하는 자막에 질문 및 응답을 업데이트시키기위해서
            List<Subtitle> subtitles = this.subtitleRepository.findAllBySubtitleId(generatedQnAUploaded.getId());
            if(subtitles.size() != 1)
                throw new InvalidSubtitleIdException();
            Subtitle subtitleToUpdate = subtitles.get(0);

            subtitleToUpdate.setQuestion(generatedQnAUploaded.getQuestion());
            subtitleToUpdate.setAnswer(generatedQnAUploaded.getAnswer());
            this.subtitleRepository.save(subtitleToUpdate);


            // 모든 자막에 질문 및 응답이 업데이트되었을 경우, 상태를 업데이트시키기 위해서
            List<Video> videos = this.videoRepository.findAllByVideoId(generatedQnAUploaded.getVideoId());
            if(videos.size() != 1)
                throw new InvalidVideoIdException();
            Video subtitleVideo = videos.get(0);

            List<Subtitle> subtitlesToCheck = this.subtitleRepository.findAllByVideo(subtitleVideo);
            int questionedSubtitleCount = subtitlesToCheck.stream().filter((subtitle) -> {
                return !((subtitle.getQuestion()==null) || (subtitle.getQuestion().isEmpty()));
            }).toArray().length;

            if(questionedSubtitleCount == subtitleVideo.getSubtitleCount())
            {
                subtitleVideo.setStatus("GeneratedQnAUploaded");
                
                Video savedVideo = this.videoRepository.save(subtitleVideo);
                videoSubscribeSocketHandler.notifyVideoUpdate(savedVideo);
            }


            CustomLogger.debug(CustomLoggerType.EXIT, "", String.format("{subtitleToUpdate: %s, questionedSubtitleCount: %d}", subtitleToUpdate.toString(), questionedSubtitleCount));

        } catch (Exception e) {
            CustomLogger.error(e, "", String.format("{generatedQnAUploaded: %s}", generatedQnAUploaded.toString()));
        }
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='VideoUploadFailed'"
    )
    public void whenVideoUploadFailed_then_UPDATE_6(
        @Payload VideoUploadFailed videoUploadFailed
    ) {
        try {

            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{videoUploadFailed: %s}", videoUploadFailed.toString()));
            if (!videoUploadFailed.validate()) return;

            CustomLogger.debug(CustomLoggerType.EFFECT, "Try to search video", String.format("{videoId: %s}", videoUploadFailed.getVideoId()));
            List<Video> videos = this.videoRepository.findAllByVideoId(videoUploadFailed.getVideoId());
            if(videos.size() != 1)
                throw new InvalidVideoIdException();
            
            Video videoToUpdate = videos.get(0);
            videoToUpdate.setStatus("VideoUploadFailed");
            Video savedVideo = this.videoRepository.save(videoToUpdate);

            videoSubscribeSocketHandler.notifyVideoUpdate(savedVideo);
            CustomLogger.debug(CustomLoggerType.EXIT);

        } catch (Exception e) {
            CustomLogger.error(e, "", String.format("{videoUploadFailed: %s}", videoUploadFailed.toString()));
        }
    }


    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='VideoRemoveRequested'"
    )
    public void whenVideoRemoveRequested_then_DELETE_1(
        @Payload VideoRemoveRequested videoRemoveRequested
    ) {
        try {

            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{videoRemoveRequested: %s}", videoRemoveRequested.toString()));
            if (!videoRemoveRequested.validate()) return;

            List<Video> videos = this.videoRepository.findAllByVideoId(videoRemoveRequested.getId());
            if(videos.size() != 1)
                throw new InvalidVideoIdException();
            Video subtitleVideo = videos.get(0);


            // 해당 비디오에 대한 자막들을 전부 삭제시키기 위해서
            List<Subtitle> subtitles = this.subtitleRepository.findAllByVideo(subtitleVideo);
            for(Subtitle subtitle : subtitles) {
                this.subtitleRepository.delete(subtitle);
            }
            this.videoRepository.delete(subtitleVideo);


            videoSubscribeSocketHandler.notifyVideoUpdate(subtitleVideo);
            CustomLogger.debug(CustomLoggerType.EXIT);

        } catch (Exception e) {
            CustomLogger.error(e, "", String.format("{videoRemoveRequested: %s}", videoRemoveRequested.toString()));
        }
    }
}
