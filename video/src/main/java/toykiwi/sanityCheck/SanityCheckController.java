package toykiwi.sanityCheck;

import toykiwi._global.Logger;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sanityCheck")
public class SanityCheckController {
    private final Logger logger;

    @GetMapping
    public void sanityCheck() {
        logger.debug();
        return;
    }
    
}
