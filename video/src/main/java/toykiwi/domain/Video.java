package toykiwi.domain;

import toykiwi.VideoApplication;
import toykiwi.event.VideoUploadRequested;
import toykiwi.event.VideoUploadNotified;
import toykiwi.dto.NotifyUploadedVideoReqDto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.PostPersist;

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

    
    @PostPersist
    public void onPostPersist() {
        VideoUploadRequested videoUploadRequested = new VideoUploadRequested(
            this
        );
        videoUploadRequested.publishAfterCommit();
    }


    public static VideoRepository repository() {
        VideoRepository videoRepository = VideoApplication.applicationContext.getBean(
            VideoRepository.class
        );
        return videoRepository;
    }

    public void notifyUploadedVideo(
        NotifyUploadedVideoReqDto notifyUploadedVideoReqDto
    ) {
        VideoUploadNotified videoUploadNotified = new VideoUploadNotified(this);
        videoUploadNotified.publishAfterCommit();
    }
}
