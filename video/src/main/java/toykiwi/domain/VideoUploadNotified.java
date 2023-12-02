package toykiwi.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import toykiwi.domain.*;
import toykiwi.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class VideoUploadNotified extends AbstractEvent {

    private Long id;
    private String youtubeUrl;
    private Integer cuttedStartSecond;
    private Integer cuttedEndSecond;
    private String uploadedUrl;

    public VideoUploadNotified(Video aggregate) {
        super(aggregate);
    }

    public VideoUploadNotified() {
        super();
    }
}
//>>> DDD / Domain Event
