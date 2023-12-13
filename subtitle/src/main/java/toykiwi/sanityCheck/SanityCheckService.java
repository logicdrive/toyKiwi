package toykiwi.sanityCheck;

import toykiwi._global.event.GeneratingQnACompleted;
import toykiwi._global.event.GeneratingSubtitleCompleted;
import toykiwi._global.event.TranslatingSubtitleCompleted;
import toykiwi._global.event.VideoRemoveRequested;
import toykiwi._global.logger.CustomLogger;
import toykiwi._global.logger.CustomLoggerType;

import toykiwi.sanityCheck.reqDtos.LogsReqDto;
import toykiwi.sanityCheck.reqDtos.MockGeneratingQnACompletedReqDto;
import toykiwi.sanityCheck.reqDtos.MockGeneratingSubtitleCompletedReqDto;
import toykiwi.sanityCheck.reqDtos.MockTranslatingSubtitleCompletedReqDto;
import toykiwi.sanityCheck.reqDtos.MockVideoRemoveRequestedReqDto;
import toykiwi.sanityCheck.resDtos.LogsResDto;

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
    public LogsResDto logs(LogsReqDto logsReqDto) throws FileNotFoundException {
            List<String> logs = new ArrayList<>();

            try {
                
                CustomLogger.debug(CustomLoggerType.EFFECT, "Try to read logs", String.format("{filePath: %s}", logFilePath));
                
                Scanner myReader = new Scanner(new File(logFilePath));
                while (myReader.hasNextLine())
                {
                    String readLog = myReader.nextLine();
                    if (logsReqDto.getRegFilter().isEmpty()) logs.add(readLog);
                    else if(readLog.matches(logsReqDto.getRegFilter())) logs.add(readLog);
                }
                myReader.close();
                
                CustomLogger.debug(CustomLoggerType.EFFECT, "Read logs", String.format("{logsSize: %d}", logs.size()));

            } catch (FileNotFoundException e) {
                CustomLogger.error(e, "Error while reading logs", String.format("{filePath: %s}", logFilePath));
                throw new FileNotFoundException();
            }

            return new LogsResDto(logs.subList(Math.max(logs.size()-logsReqDto.getLineLength(), 0), logs.size()));
    }

    
    // Policy 테스트용으로 GeneratingSubtitleCompleted 이벤트를 강제로 발생시키기 위해서
    public void mockGeneratingSubtitleCompleted(MockGeneratingSubtitleCompletedReqDto mockData) {
        (new GeneratingSubtitleCompleted(mockData)).publish();
    }

    // Policy 테스트용으로 GeneratingSubtitleCompleted 이벤트를 강제로 발생시키기 위해서
    public void mockTranslatingSubtitleCompleted(MockTranslatingSubtitleCompletedReqDto mockData) {
        (new TranslatingSubtitleCompleted(mockData)).publish();
    }

    // Policy 테스트용으로 VideoRemoveRequested 이벤트를 강제로 발생시키기 위해서
    public void mockVideoRemoveRequested(MockVideoRemoveRequestedReqDto mockData) {
        (new VideoRemoveRequested(mockData)).publish();
    }

    // Policy 테스트용으로 GeneratingQnACompleted 이벤트를 강제로 발생시키기 위해서
    public void mockGeneratingQnACompleted(MockGeneratingQnACompletedReqDto mockData) {
        (new GeneratingQnACompleted(mockData)).publish();
    }
}
