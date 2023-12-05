package toykiwi.event;

import toykiwi.domain.Video;
import toykiwi.infra.AbstractEvent;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

// 비디오 자막관련 메타데이터가 업데이트되었을 경우, 이를 알리기 위한 이벤트
@Data
@ToString
@EqualsAndHashCode(callSuper=false)
public class SubtitleMetadataUploaded extends AbstractEvent {
    private Long id;
    private String title;
    private String youtubeUrl;
    private Integer cuttedStartSecond;
    private Integer cuttedEndSecond;
    private String uploadedUrl;
    private Integer subtitleCount;

    public SubtitleMetadataUploaded(Video aggregate) {
        super(aggregate);
    }

    public SubtitleMetadataUploaded() {
        super();
    }
}
