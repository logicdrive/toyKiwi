package toykiwi.sanityCheck;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import lombok.RequiredArgsConstructor;

import toykiwi.logger.CustomLogger;
import toykiwi.logger.CustomLoggerType;
import toykiwi.sanityCheck.reqDtos.EchoToExternalSystemReqDto;
import toykiwi.sanityCheck.reqDtos.LogsReqDto;
import toykiwi.sanityCheck.reqDtos.MockGeneratedSubtitleUploadedReqDto;
import toykiwi.sanityCheck.reqDtos.MockVideoUploadRequestedReqDto;
import toykiwi.sanityCheck.reqDtos.MockVideoUrlUploadedReqDto;
import toykiwi.sanityCheck.resDtos.EchoToExternalSystemResDto;
import toykiwi.sanityCheck.resDtos.LogsResDto;

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


    // Policy 테스트용으로 VideoUploadRequested 이벤트를 강제로 발생시키기 위해서
    @PostMapping("/mock/VideoUploadRequested")
    public void mockVideoUploadRequested(@RequestBody MockVideoUploadRequestedReqDto mockData) {
        CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{mockData: %s}", mockData.toString()));
        this.sanityCheckService.mockVideoUploadRequested(mockData);
        CustomLogger.debug(CustomLoggerType.EXIT);
    }

    // Policy 테스트용으로 VideoUrlUploaded 이벤트를 강제로 발생시키기 위해서
    @PostMapping("/mock/VideoUrlUploaded")
    public void mockVideoUrlUploaded(@RequestBody MockVideoUrlUploadedReqDto mockData) {
        CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{mockData: %s}", mockData.toString()));
        this.sanityCheckService.mockVideoUrlUploaded(mockData);
        CustomLogger.debug(CustomLoggerType.EXIT);
    }

    // Policy 테스트용으로 VideoUrlUploaded 이벤트를 강제로 발생시키기 위해서
    @PostMapping("/mock/GeneratedSubtitleUploaded")
    public void mockGeneratedSubtitleUploaded(@RequestBody MockGeneratedSubtitleUploadedReqDto mockData) {
        CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{mockData: %s}", mockData.toString()));
        this.sanityCheckService.mockGeneratedSubtitleUploaded(mockData);
        CustomLogger.debug(CustomLoggerType.EXIT);
    }


    // ExternalSystem과의 JSON 기반 통신이 정상적으로 진행되는지 테스트해보기 위해서
    @PutMapping("/echoToExternalSystem")
    public ResponseEntity<EchoToExternalSystemResDto> echoToExternalSystem(@RequestBody EchoToExternalSystemReqDto echoToExternalSystemReqDto) {
        try {

            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{echoToExternalSystemReqDto: %s}", echoToExternalSystemReqDto.toString()));

            String message = this.sanityCheckService.echoToExternalSystem(echoToExternalSystemReqDto);

            CustomLogger.debug(CustomLoggerType.EXIT, "", String.format("{message: %s}", message));
            return ResponseEntity.ok(new EchoToExternalSystemResDto(message));

        } catch(Exception e) {
            CustomLogger.error(e, "", String.format("{echoToExternalSystemReqDto: %s}", echoToExternalSystemReqDto.toString()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
