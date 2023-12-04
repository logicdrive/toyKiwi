package toykiwi.event;

import toykiwi.infra.AbstractEvent;
import toykiwi.sanityCheck.MockGeneratingSubtitleCompletedReqDto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

// 자막 생성이 완료되었을 경우, 해당 자막을 기반을 Subtitle을 생성하기 위한 이벤트
@Data
@ToString
@EqualsAndHashCode(callSuper=false)
public class GeneratingSubtitleCompleted extends AbstractEvent {
    private Long videoId;
    private String subtitle;
    private Integer startSecond;
    private Integer endSecond;

    public GeneratingSubtitleCompleted(MockGeneratingSubtitleCompletedReqDto mockData) {
        super();
        this.videoId = mockData.getVideoId();
        this.subtitle = mockData.getSubtitle();
        this.startSecond = mockData.getStartSecond();
        this.endSecond = mockData.getEndSecond();
    }

    public GeneratingSubtitleCompleted() {
        super();
    }
}
