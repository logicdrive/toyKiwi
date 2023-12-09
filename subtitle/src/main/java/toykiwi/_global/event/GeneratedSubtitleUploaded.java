package toykiwi._global.event;

import toykiwi._global.infra.AbstractEvent;

import toykiwi.domain.Subtitle;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

// 생성된 자막을 기반으로 Subtitle을 만들었을 경우 발생하는 이벤트
@Data
@ToString
@EqualsAndHashCode(callSuper=false)
public class GeneratedSubtitleUploaded extends AbstractEvent {
    private Long id;
    private Long videoId;
    private String subtitle;
    private String translatedSubtitle;
    private Integer startSecond;
    private Integer endSecond;

    public GeneratedSubtitleUploaded(Subtitle aggregate) {
        super(aggregate);
    }

    public GeneratedSubtitleUploaded() {
        super();
    }
}
