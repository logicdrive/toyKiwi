package toykiwi.sanityCheck.reqDtos;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MockGeneratingSubtitleStartedReqDto {
    private Long videoId;
    private Integer subtitleCount;
}
