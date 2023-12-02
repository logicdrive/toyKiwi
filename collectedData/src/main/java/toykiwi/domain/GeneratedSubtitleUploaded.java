package toykiwi.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;
import toykiwi.infra.AbstractEvent;

@Data
public class GeneratedSubtitleUploaded extends AbstractEvent {

    private Long id;
    private Long videoId;
    private String subtitle;
    private String translatedSubtitle;
    private Integer startSecond;
    private Integer endSecond;
}
