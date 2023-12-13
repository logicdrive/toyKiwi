package toykiwi._global.event;

import toykiwi._global.infra.AbstractEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

// 자막에 대한 질문 및 응답이 생성되었을 경우, 이에 대한 정보를 업데이트하기위한 이벤트
@Data
@ToString
@EqualsAndHashCode(callSuper=false)
public class GeneratingQnACompleted extends AbstractEvent {
    private Long videoId;
    private Long subtitleId;
    private String question;
    private String answer;

    public GeneratingQnACompleted() {
        super();
    }
}
