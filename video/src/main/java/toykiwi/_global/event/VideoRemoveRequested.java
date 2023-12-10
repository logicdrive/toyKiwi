package toykiwi._global.event;

import toykiwi._global.infra.AbstractEvent;
import toykiwi.domain.Video;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

// 유저측에서 비디오 삭제를 요청했을 경우, 이를 알리기 위한 이벤트
@Data
@ToString
@EqualsAndHashCode(callSuper=false)
public class VideoRemoveRequested extends AbstractEvent {
    private Long id;
    private String title;
    private String youtubeUrl;
    private Integer cuttedStartSecond;
    private Integer cuttedEndSecond;
    private String uploadedUrl;
    private Integer subtitleCount;
    private String thumbnailUrl;

    public VideoRemoveRequested(Video aggregate) {
        super(aggregate);
    }

    public VideoRemoveRequested() {
        super();
    }
}
