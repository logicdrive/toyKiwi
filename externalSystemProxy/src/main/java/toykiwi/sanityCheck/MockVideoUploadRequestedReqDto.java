package toykiwi.sanityCheck;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MockVideoUploadRequestedReqDto {
    private Long id;
    private String title;
    private String youtubeUrl;
    private Integer cuttedStartSecond;
    private Integer cuttedEndSecond;
    private String uploadedUrl;
}
