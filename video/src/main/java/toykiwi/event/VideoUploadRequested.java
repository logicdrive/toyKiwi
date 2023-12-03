package toykiwi.event;

import toykiwi.domain.Video;
import toykiwi.infra.AbstractEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(callSuper=false)
public class VideoUploadRequested extends AbstractEvent {

    private Long id;
    private String youtubeUrl;
    private Integer cuttedStartSecond;
    private Integer cuttedEndSecond;
    private String uploadedUrl;

    public VideoUploadRequested(Video aggregate) {
        super(aggregate);
    }

    public VideoUploadRequested() {
        super();
    }
}
