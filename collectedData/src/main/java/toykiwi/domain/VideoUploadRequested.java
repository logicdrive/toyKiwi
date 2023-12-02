package toykiwi.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;
import toykiwi.infra.AbstractEvent;

@Data
public class VideoUploadRequested extends AbstractEvent {

    private Long id;
    private String youtubeUrl;
    private Integer cuttedStartSecond;
    private Integer cuttedEndSecond;
    private String uploadedUrl;
}
