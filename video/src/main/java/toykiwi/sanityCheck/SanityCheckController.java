package toykiwi.sanityCheck;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sanityCheck")
public class SanityCheckController {
    private final Logger logger = LoggerFactory.getLogger("toykiwi.video.custom");
    private final SanityCheckService sanityCheckService;

    // 정상적인 통신 여부를 단순하게 확인해보기 위해서
    @GetMapping
    public void sanityCheck() {
        logger.debug("[ENTER/EXIT] sanityCheck: {}");
        return;
    }

    // 현재 저장된 로그들 중에서 일부분을 간편하게 가져오기 위해서
    @GetMapping("/logs")
    public ResponseEntity<LogsResDto> logs(@ModelAttribute LogsReqDto logsReqDto) {
        try {

            logger.debug(String.format("[ENTER] logs: {logsReqDto: %s}", logsReqDto.toString()));

            List<String> logs = this.sanityCheckService.logs(logsReqDto);

            logger.debug(String.format("[EXIT] logs: {logsSize: %d}", logs.size()));
            return ResponseEntity.ok(new LogsResDto(logs));

        } catch(Exception e) {
            logger.error(String.format("[%s] logs: {logsReqDto: %s, stackTrace: %s}", 
                e.getClass().getName(), logsReqDto.toString(), e.getStackTrace().toString()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
