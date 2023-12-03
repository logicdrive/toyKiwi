package toykiwi.sanityCheck;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SanityCheckService {
    private final Logger logger = LoggerFactory.getLogger("toykiwi.custom");
    private final String logFilePath = "./logs/logback.log";

    // 출력된 로그들 중에서 끝부분 몇라인을 읽어서 반환시키기 위해서
    public List<String> logs(LogsReqDto logsReqDto) throws FileNotFoundException {
            List<String> logs = new ArrayList<>();

            try {
                logger.debug(String.format("[EFFECT] Read logs: {filePath: %s}", logFilePath));
                
                Scanner myReader = new Scanner(new File(logFilePath));
                while (myReader.hasNextLine()) 
                    logs.add(myReader.nextLine());
                myReader.close();
            } catch (FileNotFoundException e) {
                logger.error(String.format("[FileNotFoundException] Error while reading logs: {filePath: %s, stackTrace: %s}", 
                    logFilePath, e.getStackTrace().toString()));
                throw new FileNotFoundException();
            }

            return logs.subList(Math.max(logs.size()-logsReqDto.getLineLength(), 0), logs.size());
    }
}
