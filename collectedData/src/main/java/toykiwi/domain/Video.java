package toykiwi.domain;

import toykiwi._global.logger.CustomLogger;
import toykiwi._global.logger.CustomLoggerType;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.Data;

@Entity
@Table(name = "Video")
@Data
public class Video {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    private Long videoId;
    private String videoTitle;
    private String youtubeUrl;
    private Integer cuttedStartSecond;
    private Integer cuttedEndSecond;
    private String uploadedUrl;
    private Integer subtitleCount;
    private String thumbnailUrl;
    private String status;



    @PrePersist
    public void onPrePersist() {
        CustomLogger.debug(CustomLoggerType.EFFECT, "Try to create video by using JPA", String.format("{video: %s}", this.toString()));
    }

    @PostPersist
    public void onPostPersist() {
        CustomLogger.debug(CustomLoggerType.EFFECT, "Video is created by using JPA", String.format("{video: %s}", this.toString()));
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
    }

    @PostRemove
    public void onPostRemove() {
        CustomLogger.debug(CustomLoggerType.EFFECT, "Video is deleted by using JPA", String.format("{video: %s}", this.toString()));
    }
}
