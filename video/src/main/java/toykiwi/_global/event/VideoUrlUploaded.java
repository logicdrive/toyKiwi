package toykiwi._global.event;

import toykiwi._global.infra.AbstractEvent;
import toykiwi.domain.Video;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

// 비디오가 외부 저장소에 업로드되고, 관련 URL이 업데이트 되었을 경우, 이를 알리기 위한 이벤트
@Data
@ToString
@EqualsAndHashCode(callSuper=false)
public class VideoUrlUploaded extends AbstractEvent {
    private Long id;
    private String title;
    private String youtubeUrl;
    private Integer cuttedStartSecond;
    private Integer cuttedEndSecond;
    private String uploadedUrl;
    private Integer subtitleCount;
    private String thumbnailUrl;

    public VideoUrlUploaded(Video aggregate) {
        super(aggregate);
    }

    public VideoUrlUploaded() {
        super();
    }
}
