package toykiwi.common;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import toykiwi.VideoApplication;

@CucumberContextConfiguration
@SpringBootTest(classes = { VideoApplication.class })
public class CucumberSpingConfiguration {}
