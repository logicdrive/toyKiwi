package toykiwi._global.event;

import toykiwi._global.infra.AbstractEvent;

import toykiwi.domain.Subtitle;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

// 생성된 자막에 대한 질문 및 답변이 업데이트되었을 경우 발생하는 이벤트
@Data
@ToString
@EqualsAndHashCode(callSuper=false)
public class GeneratedQnAUploaded extends AbstractEvent {
    private Long id;
    private Long videoId;
    private String subtitle;
    private String translatedSubtitle;
    private Integer startSecond;
    private Integer endSecond;
    private String question;
    private String answer;

    public GeneratedQnAUploaded(Subtitle aggregate) {
        super(aggregate);
    }

    public GeneratedQnAUploaded() {
        super();
    }
}
