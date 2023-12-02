package toykiwi.common;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import toykiwi.SubtitleApplication;

@CucumberContextConfiguration
@SpringBootTest(classes = { SubtitleApplication.class })
public class CucumberSpingConfiguration {}
