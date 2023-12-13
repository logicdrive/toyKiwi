package toykiwi._global.event;

import toykiwi._global.infra.AbstractEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

// 비디오 삭제시, 연쇄적으로 S3에 있는 데이터를 삭제하기 위한 이벤트
@Data
@ToString
@EqualsAndHashCode(callSuper=false)
public class VideoRemovedByFail extends AbstractEvent {
    private Long videoId;
    private String uploadedUrl;
    private String thumbnailUrl;

    public VideoRemovedByFail() {
        super();
    }
}
