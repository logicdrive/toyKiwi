package toykiwi.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import toykiwi.domain.*;
import toykiwi.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class TranlatedSubtitleUploaded extends AbstractEvent {

    private Long id;
    private Long videoId;
    private String subtitle;
    private String translatedSubtitle;
    private Integer startSecond;
    private Integer endSecond;

    public TranlatedSubtitleUploaded(Subtitle aggregate) {
        super(aggregate);
    }

    public TranlatedSubtitleUploaded() {
        super();
    }
}
//>>> DDD / Domain Event
