package toykiwi.event;

import toykiwi.infra.AbstractEvent;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

// 자막 생성이 시작되었을 경우 전달되는 메타데이터들을 업데이트하기 위한 이벤트
@Data
@ToString
@EqualsAndHashCode(callSuper=false)
public class GeneratingSubtitleStarted extends AbstractEvent {
    private Long videoId;
    private Integer subtitleCount;

    public GeneratingSubtitleStarted() {
        super();
    }
}
