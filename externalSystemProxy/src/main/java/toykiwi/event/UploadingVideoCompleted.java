package toykiwi.event;

import toykiwi.infra.AbstractEvent;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

// 외부 시스템 Proxy에서 S3에 비디오 업로드를 완료했을 경우, 업로드 URL을 업데이트하기위한 이벤트
@Data
@ToString
@EqualsAndHashCode(callSuper=false)
public class UploadingVideoCompleted extends AbstractEvent {
    private Long videoId;
    private String videoTitle;
    private String uploadedUrl;
    private String thumbnailUrl;

    public UploadingVideoCompleted() {
        super();
    }
}
