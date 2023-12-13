package toykiwi.domain;

import toykiwi.SubtitleApplication;
import toykiwi._global.event.GeneratedQnAUploaded;
import toykiwi._global.event.GeneratedSubtitleUploaded;
import toykiwi._global.event.GeneratingQnACompleted;
import toykiwi._global.event.GeneratingSubtitleCompleted;
import toykiwi._global.event.TranlatedSubtitleUploaded;
import toykiwi._global.event.TranslatingSubtitleCompleted;
import toykiwi._global.event.VideoRemoveRequested;
import toykiwi._global.logger.CustomLogger;
import toykiwi._global.logger.CustomLoggerType;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import lombok.Data;

@Entity
@Table(name = "Subtitle")
@Data
public class Subtitle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long videoId;

    private String subtitle;

    private String translatedSubtitle;

    private Integer startSecond;

    private Integer endSecond;

    private String question;

    private String answer;

    public static SubtitleRepository repository() {
        SubtitleRepository subtitleRepository = SubtitleApplication.applicationContext.getBean(
            SubtitleRepository.class
        );
        return subtitleRepository;
    }



    @PrePersist
    public void onPrePersist() {
        CustomLogger.debug(CustomLoggerType.EFFECT, "Try to create subtitle by using JPA", String.format("{subtitle: %s}", this.toString()));
    }

    @PostPersist
    public void onPostPersist() {
        CustomLogger.debug(CustomLoggerType.EFFECT, "Subtitle is created by using JPA", String.format("{subtitle: %s}", this.toString()));
    }


    @PreUpdate
    public void onPreUpdate() {
        CustomLogger.debug(CustomLoggerType.EFFECT, "Try to update subtitle by using JPA", String.format("{subtitle: %s}", this.toString()));
    }

    @PostUpdate
    public void onPostUpdate() {
        CustomLogger.debug(CustomLoggerType.EFFECT, "Subtitle is updated by using JPA", String.format("{subtitle: %s}", this.toString()));
    }


    @PreRemove
    public void onPreRemove() {
        CustomLogger.debug(CustomLoggerType.EFFECT, "Try to delete subtitle by using JPA", String.format("{subtitle: %s}", this.toString()));
    }

    @PostRemove
    public void onPostRemove() {
        CustomLogger.debug(CustomLoggerType.EFFECT, "Subtitle is deleted by using JPA", String.format("{subtitle: %s}", this.toString()));
    }


    // 자막이 생성되었을 경우, 생성된 자막 관련 정보를 새로 추가시키기 위해서
    public static void uploadGeneratedSubtitle(
        GeneratingSubtitleCompleted generatingSubtitleCompleted
    ) {

        Subtitle subtitle = new Subtitle();
        subtitle.setVideoId(generatingSubtitleCompleted.getVideoId());
        subtitle.setSubtitle(generatingSubtitleCompleted.getSubtitle());
        subtitle.setStartSecond(generatingSubtitleCompleted.getStartSecond());
        subtitle.setEndSecond(generatingSubtitleCompleted.getEndSecond());
        Subtitle createdSubtitle = repository().save(subtitle);

        GeneratedSubtitleUploaded generatedSubtitleUploaded = new GeneratedSubtitleUploaded(createdSubtitle);
        generatedSubtitleUploaded.publishAfterCommit();

    }

    // 자막에 대한 번역문이 생성되었을 경우, 해당 번역문을 업데이트시키기 위해서
    public static void uploadTranslatedSubtitle(
        TranslatingSubtitleCompleted translatingSubtitleCompleted
    ) {

        CustomLogger.debug(CustomLoggerType.EFFECT, "Try to search Subtitle by using JPA", String.format("{translatingSubtitleCompleted: %s}", translatingSubtitleCompleted.toString()));
        repository().findById(translatingSubtitleCompleted.getSubtitleId()).ifPresent(subtitle->{
            
            CustomLogger.debug(CustomLoggerType.EFFECT, "Subtitle is searched by using JPA", String.format("{subtitle: %s}", subtitle.toString()));

            subtitle.setTranslatedSubtitle(translatingSubtitleCompleted.getTranslatedSubtitle());
            repository().save(subtitle);


            TranlatedSubtitleUploaded tranlatedSubtitleUploaded = new TranlatedSubtitleUploaded(subtitle);
            tranlatedSubtitleUploaded.publishAfterCommit();

         });

    }

    // 자막에 대한 질문 및 응답이 생성되었을 경우, 해당 내용을 업데이트시키기 위해서
    public static void uploadGeneratedQnA(
        GeneratingQnACompleted generatingQnACompleted
    ) {

        CustomLogger.debug(CustomLoggerType.EFFECT, "Try to search Subtitle by using JPA", String.format("{generatingQnACompleted: %s}", generatingQnACompleted.toString()));
        repository().findById(generatingQnACompleted.getSubtitleId()).ifPresent(subtitle->{
            
            CustomLogger.debug(CustomLoggerType.EFFECT, "Subtitle is searched by using JPA", String.format("{subtitle: %s}", subtitle.toString()));

            subtitle.setQuestion(generatingQnACompleted.getQuestion());
            subtitle.setAnswer(generatingQnACompleted.getAnswer());
            repository().save(subtitle);


            GeneratedQnAUploaded generatedQnAUploaded = new GeneratedQnAUploaded(subtitle);
            generatedQnAUploaded.publishAfterCommit();

         });

    }


    // 비디오 삭제 요청이 있을 경우, 관련된 자막들을 전부 삭제시키기 위해서
    public static void removeSubtitles(
        VideoRemoveRequested videoRemoveRequested
    ) {
        List<Subtitle> subtitles = repository().findAllByVideoId(videoRemoveRequested.getId());
        for(Subtitle subtitle : subtitles) {
            repository().delete(subtitle);
        }
    }
}
