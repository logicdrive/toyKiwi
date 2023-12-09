package toykiwi._global.event;

import toykiwi._global.infra.AbstractEvent;

import toykiwi.domain.Subtitle;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

// 생성된 자막에 대한 번역문에 대한 업데이트를 완료했을 경우 발생하는 이벤트
@Data
@ToString
@EqualsAndHashCode(callSuper=false)
public class TranlatedSubtitleUploaded extends AbstractEvent {
    private Long id;
    private Long videoId;
    private String subtitle;
    private String translatedSubtitle;
    private Integer startSecond;
    private Integer endSecond;

    public TranlatedSubtitleUploaded(Subtitle aggregate) {
        super(aggregate);
    }

    public TranlatedSubtitleUploaded() {
        super();
    }
}
