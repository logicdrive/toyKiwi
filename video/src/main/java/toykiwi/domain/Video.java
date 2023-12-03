package toykiwi.domain;

import toykiwi.VideoApplication;
import toykiwi.event.UploadingVideoCompleted;
import toykiwi.event.VideoUploadRequested;
import toykiwi.event.VideoUrlUploaded;
import toykiwi.logger.CustomLogger;
import toykiwi.logger.CustomLoggerType;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Data;

@Entity
@Table(name = "Video")
@Data
public class Video {    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String youtubeUrl;

    private Integer cuttedStartSecond;

    private Integer cuttedEndSecond;

    private String uploadedUrl;

    public static VideoRepository repository() {
        VideoRepository videoRepository = VideoApplication.applicationContext.getBean(
            VideoRepository.class
        );
        return videoRepository;
    }



    @PrePersist
    public void onPrePersist() {
        CustomLogger.debug(CustomLoggerType.EFFECT, "Try to create video by using JPA", String.format("{Video: %s}", this.toString()));
    }

    @PostPersist
    public void onPostPersist() {
        CustomLogger.debug(CustomLoggerType.EFFECT, "Video is created by using JPA", String.format("{Video: %s}", this.toString()));

        VideoUploadRequested videoUploadRequested = new VideoUploadRequested(
            this
        );
        videoUploadRequested.publishAfterCommit();
    }


    @PreUpdate
    public void onPreUpdate() {
        CustomLogger.debug(CustomLoggerType.EFFECT, "Try to update video by using JPA", String.format("{Video: %s}", this.toString()));
    }

    @PostUpdate
    public void onPostUpdate() {
        CustomLogger.debug(CustomLoggerType.EFFECT, "Video is updated by using JPA", String.format("{Video: %s}", this.toString()));
    }


    // 외부 저장소에서 URL이 업로드 되었을 경우, 그 URL을 반영시키고, 관련 이벤트를 발생시키기 위해서
    public static void uploadVideoUrl(UploadingVideoCompleted uploadingVideoCompleted) {
        CustomLogger.debug(CustomLoggerType.EFFECT, "Try to search Video by using JPA", String.format("{UploadingVideoCompleted: %s}", uploadingVideoCompleted.toString()));
        repository().findById(uploadingVideoCompleted.getVideoId()).ifPresent(video->{
            
            CustomLogger.debug(CustomLoggerType.EFFECT, "Video is searched by using JPA", String.format("{Video: %s}", video.toString()));

            video.setUploadedUrl(uploadingVideoCompleted.getUploadedUrl());
            repository().save(video);

            
            VideoUrlUploaded videoUrlUploaded = new VideoUrlUploaded(video);
            videoUrlUploaded.publishAfterCommit();

         });
    }
}
