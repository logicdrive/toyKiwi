package toykiwi.sanityCheck;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;

import toykiwi.event.GeneratedSubtitleUploaded;
import toykiwi.event.VideoUploadRequested;
import toykiwi.event.VideoUrlUploaded;
import toykiwi.logger.CustomLogger;
import toykiwi.logger.CustomLoggerType;
import toykiwi.sanityCheck.reqDtos.EchoToExternalSystemReqDto;
import toykiwi.sanityCheck.reqDtos.EchoWithJsonReqDto;
import toykiwi.sanityCheck.reqDtos.LogsReqDto;
import toykiwi.sanityCheck.reqDtos.MockGeneratedSubtitleUploadedReqDto;
import toykiwi.sanityCheck.reqDtos.MockVideoUploadRequestedReqDto;
import toykiwi.sanityCheck.reqDtos.MockVideoUrlUploadedReqDto;
import toykiwi.sanityCheck.resDtos.EchoWithJsonResDto;

import com.fasterxml.jackson.databind.ObjectMapper;

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


    // ExternalSystem과의 JSON 기반 통신이 정상적으로 진행되는지 테스트해보기 위해서
    public String echoToExternalSystem(EchoToExternalSystemReqDto echoToExternalSystemReqDto) throws Exception {
            
        EchoWithJsonReqDto echoWithJsonReqDto = new EchoWithJsonReqDto(echoToExternalSystemReqDto.getMessage());
        CustomLogger.debug(CustomLoggerType.EFFECT, "Request to external system", String.format("{echoWithJsonReqDto: %s}", echoWithJsonReqDto));
        
        try {

                String resultRawText = WebClient.create("http://localhost:8085/sanityCheck/echoWithJson")
                    .put()
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(echoWithJsonReqDto.hashMap()))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
                CustomLogger.debug(CustomLoggerType.EFFECT, "Read results from external system", String.format("{resultRawText: %s}", resultRawText));

                ObjectMapper mapper = new ObjectMapper();
                EchoWithJsonResDto echoWithJsonResDto = mapper.readValue(resultRawText, EchoWithJsonResDto.class);
                CustomLogger.debug(CustomLoggerType.EFFECT, "Read results from external system", String.format("{echoWithJsonResDto: %s}", echoWithJsonResDto));

                return echoWithJsonReqDto.getMessage();

        } catch (Exception e) {
            CustomLogger.error(e, "Error while requesting to externalSystem", String.format("{echoWithJsonReqDto: %s}", echoWithJsonReqDto));
            throw e;
        }

    }
}
