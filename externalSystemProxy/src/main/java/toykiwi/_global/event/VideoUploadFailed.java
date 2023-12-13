package toykiwi._global.event;

import toykiwi._global.infra.AbstractEvent;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

// 비디오 업로드 과정에서 실패가 발생했을시, 관련 데이터를 삭제시키기 위한 이벤트
@Data
@ToString
@EqualsAndHashCode(callSuper=false)
public class VideoUploadFailed extends AbstractEvent {
    private Long videoId;

    public VideoUploadFailed(Long videoId) {
        super();
        this.videoId = videoId;
    }

    public VideoUploadFailed() {
        super();
    }
}
