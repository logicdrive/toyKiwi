package toykiwi.domain;

import toykiwi._global.logger.CustomLogger;
import toykiwi._global.logger.CustomLoggerType;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "Subtitle")
@Data
public class Subtitle {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private Long subtitleId;
    private String subtitle;
    private String translatedSubtitle;
    private Integer startSecond;
    private Integer endSecond;
    private String question;
    private String answer;

    @ManyToOne
    @JoinColumn(name = "videoId", nullable = false)
    private Video video;



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
}
