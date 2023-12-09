package toykiwi.sanityCheck.reqDtos;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MockGeneratingSubtitleCompletedReqDto {
    private Long videoId;
    private String subtitle;
    private Integer startSecond;
    private Integer endSecond;
}
