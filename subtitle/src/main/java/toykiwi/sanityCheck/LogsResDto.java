package toykiwi.sanityCheck;

import java.util.List;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class LogsResDto {
    private final List<String> logs;

    public LogsResDto(List<String> logs) {
        this.logs = logs;
    }
}
