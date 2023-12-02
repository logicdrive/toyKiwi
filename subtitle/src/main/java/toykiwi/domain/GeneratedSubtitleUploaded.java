package toykiwi.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import toykiwi.domain.*;
import toykiwi.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class GeneratedSubtitleUploaded extends AbstractEvent {

    private Long id;
    private Long videoId;
    private String subtitle;
    private String translatedSubtitle;
    private Integer startSecond;
    private Integer endSecond;

    public GeneratedSubtitleUploaded(Subtitle aggregate) {
        super(aggregate);
    }

    public GeneratedSubtitleUploaded() {
        super();
    }
}
//>>> DDD / Domain Event
