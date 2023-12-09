package toykiwi.sanityCheck.resDtos;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MockTranslatingSubtitleCompletedReqDto {
    private Long subtitleId;
    private String translatedSubtitle;
}
