package toykiwi.sanityCheck;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MockUploadingVideoCompletedReqDto {
    private Long videoId;
    private String uploadedUrl;
}
