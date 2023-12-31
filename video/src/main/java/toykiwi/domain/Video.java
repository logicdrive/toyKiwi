package toykiwi.domain;

import toykiwi.VideoApplication;
import toykiwi._global.event.GeneratingSubtitleStarted;
import toykiwi._global.event.SubtitleMetadataUploaded;
import toykiwi._global.event.UploadingVideoCompleted;
import toykiwi._global.event.VideoRemoveRequested;
import toykiwi._global.event.VideoRemovedByFail;
import toykiwi._global.event.VideoUploadFailed;
import toykiwi._global.event.VideoUploadRequested;
import toykiwi._global.event.VideoUrlUploaded;
import toykiwi._global.logger.CustomLogger;
import toykiwi._global.logger.CustomLoggerType;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PostRemove;
import javax.persistence.PreUpdate;

import lombok.Data;

@Entity
@Table(name = "Video")
@Data
public class Video {    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String youtubeUrl;

    private Integer cuttedStartSecond;

    private Integer cuttedEndSecond;

    private String uploadedUrl;

    private Integer subtitleCount;

    private String thumbnailUrl;

    public static VideoRepository repository() {
        VideoRepository videoRepository = VideoApplication.applicationContext.getBean(
            VideoRepository.class
        );
        return videoRepository;
    }



    @PrePersist
    public void onPrePersist() {
        CustomLogger.debug(CustomLoggerType.EFFECT, "Try to create video by using JPA", String.format("{video: %s}", this.toString()));
    }

    @PostPersist
    public void onPostPersist() {
        CustomLogger.debug(CustomLoggerType.EFFECT, "Video is created by using JPA", String.format("{video: %s}", this.toString()));

        VideoUploadRequested videoUploadRequested = new VideoUploadRequested(
            this
        );
        videoUploadRequested.publishAfterCommit();
    }


    @PreUpdate
    public void onPreUpdate() {
        CustomLogger.debug(CustomLoggerType.EFFECT, "Try to update video by using JPA", String.format("{video: %s}", this.toString()));
    }

    @PostUpdate
    public void onPostUpdate() {
        CustomLogger.debug(CustomLoggerType.EFFECT, "Video is updated by using JPA", String.format("{video: %s}", this.toString()));
    }


    @PreRemove
    public void onPreRemove() {
        CustomLogger.debug(CustomLoggerType.EFFECT, "Try to delete video by using JPA", String.format("{video: %s}", this.toString()));
        VideoRemoveRequested videoDeleteRequested = new VideoRemoveRequested(this);
        videoDeleteRequested.publishAfterCommit();
    }

    @PostRemove
    public void onPostRemove() {
        CustomLogger.debug(CustomLoggerType.EFFECT, "Video is deleted video by using JPA", String.format("{video: %s}", this.toString()));
    }


    // 외부 저장소에서 URL이 업로드 되었을 경우, 그 URL을 반영시키고, 관련 이벤트를 발생시키기 위해서
    public static void uploadVideoUrl(UploadingVideoCompleted uploadingVideoCompleted) {
        CustomLogger.debug(CustomLoggerType.EFFECT, "Try to search Video by using JPA", String.format("{uploadingVideoCompleted: %s}", uploadingVideoCompleted.toString()));
        repository().findById(uploadingVideoCompleted.getVideoId()).ifPresent(video->{
            
            CustomLogger.debug(CustomLoggerType.EFFECT, "Video is searched by using JPA", String.format("{video: %s}", video.toString()));

            video.setTitle(uploadingVideoCompleted.getVideoTitle());
            video.setUploadedUrl(uploadingVideoCompleted.getUploadedUrl());
            video.setThumbnailUrl(uploadingVideoCompleted.getThumbnailUrl());
            repository().save(video);

            
            VideoUrlUploaded videoUrlUploaded = new VideoUrlUploaded(video);
            videoUrlUploaded.publishAfterCommit();

         });
    }

    // 자막 생성이 시작되었을 경우, 관련 메타데이터들을 업데이트하기 위해서
    public static void uploadSubtitleMetadata(GeneratingSubtitleStarted generatingSubtitleStarted) {
        CustomLogger.debug(CustomLoggerType.EFFECT, "Try to search Video by using JPA", String.format("{generatingSubtitleStarted: %s}", generatingSubtitleStarted.toString()));
        repository().findById(generatingSubtitleStarted.getVideoId()).ifPresent(video->{
            
            CustomLogger.debug(CustomLoggerType.EFFECT, "Video is searched by using JPA", String.format("{video: %s}", video.toString()));

            video.setSubtitleCount(generatingSubtitleStarted.getSubtitleCount());
            repository().save(video);

            
            SubtitleMetadataUploaded subtitleMetadataUploaded = new SubtitleMetadataUploaded(video);
            subtitleMetadataUploaded.publishAfterCommit();

         });
    }


    // 비디오 업로드 실패시 관련 데이터를 삭제시키기 위해서
    public static void removeVideoByFail(VideoUploadFailed videoUploadFailed) {
        CustomLogger.debug(CustomLoggerType.EFFECT, "Try to search Video by using JPA", String.format("{videoUploadFailed: %s}", videoUploadFailed.toString()));
        repository().findById(videoUploadFailed.getVideoId()).ifPresent(video->{
            repository().delete(video);

            VideoRemovedByFail videoRemovedByFail = new VideoRemovedByFail(video);
            videoRemovedByFail.publishAfterCommit();
         });
    }
}
