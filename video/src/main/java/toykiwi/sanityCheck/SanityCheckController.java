package toykiwi.sanityCheck;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import lombok.RequiredArgsConstructor;
import toykiwi.logger.CustomLogger;
import toykiwi.logger.CustomLoggerType;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sanityCheck")
public class SanityCheckController {
    private final SanityCheckService sanityCheckService;

    // 정상적인 통신 여부를 단순하게 확인해보기 위해서
    @GetMapping
    public void sanityCheck() {
        CustomLogger.debug(CustomLoggerType.ENTER_EXIT);
        return;
    }

    // 현재 저장된 로그들 중에서 일부분을 간편하게 가져오기 위해서
    @GetMapping("/logs")
    public ResponseEntity<LogsResDto> logs(@ModelAttribute LogsReqDto logsReqDto) {
        try {

            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{logsReqDto: %s}", logsReqDto.toString()));

            List<String> logs = this.sanityCheckService.logs(logsReqDto);

            CustomLogger.debug(CustomLoggerType.EXIT, "", String.format("{logsSize: %d}", logs.size()));
            return ResponseEntity.ok(new LogsResDto(logs));

        } catch(Exception e) {
            CustomLogger.error(e, "", String.format("{logsReqDto: %s}", logsReqDto.toString()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    
    // Policy 테스트용으로 UploadingVideoCompleted 이벤트를 강제로 발생시키기 위해서
    @PostMapping("/mock/UploadingVideoCompleted")
    public void mockUploadingVideoCompleted(@RequestBody MockUploadingVideoCompletedReqDto mockData) {
        CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{mockData: %s}", mockData.toString()));
        this.sanityCheckService.mockUploadingVideoCompleted(mockData);
        CustomLogger.debug(CustomLoggerType.EXIT);
    }

    // Policy 테스트용으로 GeneratingSubtitleStarted 이벤트를 강제로 발생시키기 위해서
    @PostMapping("/mock/GeneratingSubtitleStarted")
    public void mockGeneratingSubtitleStarted(@RequestBody MockGeneratingSubtitleStartedReqDto mockData) {
        CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{mockData: %s}", mockData.toString()));
        this.sanityCheckService.mockGeneratingSubtitleStarted(mockData);
        CustomLogger.debug(CustomLoggerType.EXIT);
    }
}
