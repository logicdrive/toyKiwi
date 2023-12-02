package toykiwi.domain;

import java.util.*;
import lombok.*;
import toykiwi.domain.*;
import toykiwi.infra.AbstractEvent;

@Data
@ToString
public class VideoUploadNotified extends AbstractEvent {

    private Long id;
    private String youtubeUrl;
    private Integer cuttedStartSecond;
    private Integer cuttedEndSecond;
    private String uploadedUrl;
}
