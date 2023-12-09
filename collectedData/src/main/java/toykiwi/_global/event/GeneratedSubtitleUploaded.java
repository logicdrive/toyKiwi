package toykiwi._global.event;

import toykiwi._global.infra.AbstractEvent;

import toykiwi.sanityCheck.reqDtos.MockGeneratedSubtitleUploadedReqDto;

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

    public GeneratedSubtitleUploaded(MockGeneratedSubtitleUploadedReqDto mockData) {
        super();
        this.id = mockData.getId();
        this.videoId = mockData.getVideoId();
        this.subtitle = mockData.getSubtitle();
        this.translatedSubtitle = mockData.getTranslatedSubtitle();
        this.startSecond = mockData.getStartSecond();
        this.endSecond = mockData.getEndSecond();
    }

    public GeneratedSubtitleUploaded() {
        super();
    }
}
