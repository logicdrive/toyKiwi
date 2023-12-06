package toykiwi.sanityCheck;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MockGeneratedSubtitleUploadedReqDto {
    private Long id;
    private Long videoId;
    private String subtitle;
    private String translatedSubtitle;
    private Integer startSecond;
    private Integer endSecond;
}
