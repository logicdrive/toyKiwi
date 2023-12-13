package toykiwi._global.event;

import toykiwi._global.infra.AbstractEvent;
import toykiwi.sanityCheck.reqDtos.MockTranslatingSubtitleCompletedReqDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

// 자막에 대한 번역문 생성이 완료되었을 경우, 해당 내용을 Subtitle에 반영하기 위한 이벤트
@Data
@ToString
@EqualsAndHashCode(callSuper=false)
public class TranslatingSubtitleCompleted extends AbstractEvent {
    private Long videoId;
    private Long subtitleId;
    private String translatedSubtitle;

    public TranslatingSubtitleCompleted(MockTranslatingSubtitleCompletedReqDto mockData) {
        super();
        this.videoId = mockData.getVideoId();
        this.subtitleId = mockData.getSubtitleId();
        this.translatedSubtitle = mockData.getTranslatedSubtitle();
    }

    public TranslatingSubtitleCompleted() {
        super();
    }
}
