package toykiwi._global.event;

import toykiwi._global.infra.AbstractEvent;

import toykiwi.sanityCheck.reqDtos.MockTranlatedSubtitleUploadedReqDto;

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
    private String question;
    private String answer;

    public TranlatedSubtitleUploaded(MockTranlatedSubtitleUploadedReqDto mockData) {
        super();
        this.id = mockData.getId();
        this.videoId = mockData.getVideoId();
        this.subtitle = mockData.getSubtitle();
        this.translatedSubtitle = mockData.getTranslatedSubtitle();
        this.startSecond = mockData.getStartSecond();
        this.endSecond = mockData.getEndSecond();
        this.question = mockData.getQuestion();
        this.answer = mockData.getAnswer();
    }

    public TranlatedSubtitleUploaded() {
        super();
    }
}
