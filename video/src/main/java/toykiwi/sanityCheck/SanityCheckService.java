package toykiwi.sanityCheck;

import toykiwi._global.event.GeneratingSubtitleStarted;
import toykiwi._global.event.UploadingVideoCompleted;
import toykiwi._global.logger.CustomLogger;
import toykiwi._global.logger.CustomLoggerType;

import toykiwi.sanityCheck.reqDtos.LogsReqDto;
import toykiwi.sanityCheck.reqDtos.MockGeneratingSubtitleStartedReqDto;
import toykiwi.sanityCheck.reqDtos.MockUploadingVideoCompletedReqDto;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;

@Service
public class SanityCheckService {
    private final String logFilePath = "./logs/logback.log";

    // 출력된 로그들 중에서 끝부분 몇라인을 읽어서 반환시키기 위해서
    public List<String> logs(LogsReqDto logsReqDto) throws FileNotFoundException {
            List<String> logs = new ArrayList<>();

            try {
                
                CustomLogger.debug(CustomLoggerType.EFFECT, "Try to read logs", String.format("{filePath: %s}", logFilePath));
                
                Scanner myReader = new Scanner(new File(logFilePath));
                while (myReader.hasNextLine()) 
                    logs.add(myReader.nextLine());
                myReader.close();
                
                CustomLogger.debug(CustomLoggerType.EFFECT, "Read logs", String.format("{logsSize: %d}", logs.size()));

            } catch (FileNotFoundException e) {
                CustomLogger.error(e, "Error while reading logs", String.format("{filePath: %s}", logFilePath));
                throw new FileNotFoundException();
            }

            return logs.subList(Math.max(logs.size()-logsReqDto.getLineLength(), 0), logs.size());
    }


    // Policy 테스트용으로 UploadingVideoCompleted 이벤트를 강제로 발생시키기 위해서
    public void mockUploadingVideoCompleted(MockUploadingVideoCompletedReqDto mockData) {
        (new UploadingVideoCompleted(mockData)).publish();
    }

    // Policy 테스트용으로 GeneratingSubtitleStarted 이벤트를 강제로 발생시키기 위해서
    public void mockGeneratingSubtitleStarted(MockGeneratingSubtitleStartedReqDto mockData) {
        (new GeneratingSubtitleStarted(mockData)).publish();
    }
}
