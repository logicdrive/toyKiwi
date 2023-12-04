package toykiwi.sanityCheck;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import toykiwi.event.GeneratedSubtitleUploaded;
import toykiwi.event.VideoUploadRequested;
import toykiwi.event.VideoUrlUploaded;
import toykiwi.logger.CustomLogger;
import toykiwi.logger.CustomLoggerType;

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


    // Policy 테스트용으로 VideoUploadRequested 이벤트를 강제로 발생시키기 위해서
    public void mockVideoUploadRequested(MockVideoUploadRequestedReqDto mockData) {
        (new VideoUploadRequested(mockData)).publish();
    }

    // Policy 테스트용으로 VideoUrlUploaded 이벤트를 강제로 발생시키기 위해서
    public void mockVideoUrlUploaded(MockVideoUrlUploadedReqDto mockData) {
        (new VideoUrlUploaded(mockData)).publish();
    }

    // Policy 테스트용으로 GeneratedSubtitleUploaded 이벤트를 강제로 발생시키기 위해서
    public void mockGeneratedSubtitleUploaded(MockGeneratedSubtitleUploadedReqDto mockData) {
        (new GeneratedSubtitleUploaded(mockData)).publish();
    }
}
