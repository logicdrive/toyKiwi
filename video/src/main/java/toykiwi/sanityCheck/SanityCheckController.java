package toykiwi.sanityCheck;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sanityCheck")
public class SanityCheckController {
    private final Logger logger = LoggerFactory.getLogger("toykiwi.custom");

    @GetMapping
    public void sanityCheck() {
        logger.info("Info Message");
        logger.warn("Warn Message");
        logger.error("Error Message");
        return;
    }
}
