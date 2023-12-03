package toykiwi.sanityCheck;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    private final Logger logger = LoggerFactory.getLogger("toykiwi.custom");
    private final SanityCheckService sanityCheckService;

    // 정상적인 통신 여부를 단순하게 확인해보기 위해서
    @GetMapping
    public void sanityCheck() {
        logger.debug("[ENTER/EXIT] sanityCheck: {}");
        return;
    }

    // 현재 저장된 로그들 중에서 일부분을 간편하게 가져오기 위해서
    @GetMapping("/logs")
    public LogsResDto logs(@ModelAttribute LogsReqDto logsReqDto) {
        logger.debug(String.format("[ENTER] logs: {logsReqDto.toString(): %s}", logsReqDto.toString()));

        List<String> logs = this.sanityCheckService.logs(logsReqDto);

        logger.debug(String.format("[EXIT] logs: {logs.size(): %d}", logs.size()));
        return new LogsResDto(logs);
    }
}
