package toykiwi.sanityCheck.reqDtos;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MockUploadingVideoCompletedReqDto {
    private Long videoId;
    private String videoTitle;
    private String uploadedUrl;
    private String thumbnailUrl;
}
