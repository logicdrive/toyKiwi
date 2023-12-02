package toykiwi.common;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import toykiwi.CollectedDataApplication;

@CucumberContextConfiguration
@SpringBootTest(classes = { CollectedDataApplication.class })
public class CucumberSpingConfiguration {}
