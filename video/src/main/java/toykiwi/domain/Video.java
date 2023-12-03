package toykiwi.domain;

import toykiwi.VideoApplication;
import toykiwi.event.VideoUploadRequested;
import toykiwi.event.VideoUploadNotified;
import toykiwi.dto.NotifyUploadedVideoReqDto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    @Transient
    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger("toykiwi.video.custom");

    
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
        logger.debug(String.format("[EFFECT] Try to create video by using JPA: {Video: %s}", this.toString()));
    }

    @PostPersist
    public void onPostPersist() {
        VideoUploadRequested videoUploadRequested = new VideoUploadRequested(
            this
        );
        videoUploadRequested.publishAfterCommit();

        logger.debug(String.format("[EFFECT] Video is created by using JPA: {Video: %s}", this.toString()));
    }


    @PreUpdate
    public void onPreUpdate() {
        logger.debug(String.format("[EFFECT] Try to update video by using JPA: {Video: %s}", this.toString()));
    }

    @PostUpdate
    public void onPostUpdate() {
        logger.debug(String.format("[EFFECT] Video is updated by using JPA: {Video: %s}", this.toString()));
    }

    

    // 외부 저장소에서 URL이 업로드 되었을 경우, 그 URL을 반영시키고, 관련 이벤트를 발생시키기 위해서
    public void notifyUploadedVideo(
        NotifyUploadedVideoReqDto notifyUploadedVideoReqDto
    ) {
        VideoUploadNotified videoUploadNotified = new VideoUploadNotified(this);
        videoUploadNotified.publishAfterCommit();
    }
}
