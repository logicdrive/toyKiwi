package toykiwi.collectedData.resDtos;

import toykiwi.domain.Subtitle;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SubtitleResDto {
    private final Long id;
    private final Long subtitleId;
    private final String subtitle;
    private final String translatedSubtitle;
    private final Integer startSecond;
    private final Integer endSecond;
    private final Long videoId;

    public SubtitleResDto(Subtitle subtitle) {
        this.id = subtitle.getId();
        this.subtitleId = subtitle.getSubtitleId();
        this.subtitle = subtitle.getSubtitle();
        this.translatedSubtitle = subtitle.getTranslatedSubtitle();
        this.startSecond = subtitle.getStartSecond();
        this.endSecond = subtitle.getEndSecond();
        this.videoId = subtitle.getVideo().getVideoId();
    }
}