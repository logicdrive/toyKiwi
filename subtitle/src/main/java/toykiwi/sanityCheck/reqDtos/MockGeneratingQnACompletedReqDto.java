package toykiwi.sanityCheck.reqDtos;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MockGeneratingQnACompletedReqDto {
    private Long videoId;
    private Long subtitleId;
    private String question;
    private String answer;
}
