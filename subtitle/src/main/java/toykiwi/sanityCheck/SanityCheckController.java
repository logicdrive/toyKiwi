package toykiwi.sanityCheck;

import toykiwi._global.logger.CustomLogger;
import toykiwi._global.logger.CustomLoggerType;

import toykiwi.sanityCheck.reqDtos.LogsReqDto;
import toykiwi.sanityCheck.reqDtos.MockGeneratingSubtitleCompletedReqDto;
import toykiwi.sanityCheck.resDtos.LogsResDto;
import toykiwi.sanityCheck.resDtos.MockTranslatingSubtitleCompletedReqDto;

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

    // 정상적인 에러 로그 출력 여부를 테스트해보기 위해서
    @GetMapping("/divByZeroCheck")
    public ResponseEntity<Integer> divByZeroCheck() {
        try {
            Integer returnNum = 1/0;
            return ResponseEntity.ok(returnNum);
        } catch(Exception e) {
            CustomLogger.error(e, "Div By Zero Check Message", String.format("{returnNum: %s}", "Undefined"));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }    
    }

    
    // Policy 테스트용으로 GeneratingSubtitleCompleted 이벤트를 강제로 발생시키기 위해서
    @PostMapping("/mock/GeneratingSubtitleCompleted")
    public void mockGeneratingSubtitleCompleted(@RequestBody MockGeneratingSubtitleCompletedReqDto mockData) {
        CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{mockData: %s}", mockData.toString()));
        this.sanityCheckService.mockGeneratingSubtitleCompleted(mockData);
        CustomLogger.debug(CustomLoggerType.EXIT);
    }

    // Policy 테스트용으로 TranslatingSubtitleCompleted 이벤트를 강제로 발생시키기 위해서
    @PostMapping("/mock/TranslatingSubtitleCompleted")
    public void mockTranslatingSubtitleCompleted(@RequestBody MockTranslatingSubtitleCompletedReqDto mockData) {
        CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{mockData: %s}", mockData.toString()));
        this.sanityCheckService.mockTranslatingSubtitleCompleted(mockData);
        CustomLogger.debug(CustomLoggerType.EXIT);
    }
}
