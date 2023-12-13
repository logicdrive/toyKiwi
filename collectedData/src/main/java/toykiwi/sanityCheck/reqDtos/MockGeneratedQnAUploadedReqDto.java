package toykiwi.sanityCheck.reqDtos;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MockGeneratedQnAUploadedReqDto {
    private Long id;
    private Long videoId;
    private String subtitle;
    private String translatedSubtitle;
    private Integer startSecond;
    private Integer endSecond;
    private String question;
    private String answer;
}
