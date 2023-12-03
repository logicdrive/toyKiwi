package toykiwi.sanityCheck;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class SanityCheckService {
    public List<String> logs(LogsReqDto logsReqDto) {
        List<String> logs = new ArrayList<>();
        logs.add("LOGS");
        logs.add(logsReqDto.getLineLength().toString());
        return logs;
    }
}
