package toykiwi.event;

import toykiwi.domain.Video;
import toykiwi.infra.AbstractEvent;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

// 유저측에서 비디오 업로드를 요청했을 경우, 이를 알리기 위한 이벤트
@Data
@ToString
@EqualsAndHashCode(callSuper=false)
public class VideoUploadRequested extends AbstractEvent {
    private Long id;
    private String title;
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
