package toykiwi.common;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import toykiwi.ExternalSystemProxyApplication;

@CucumberContextConfiguration
@SpringBootTest(classes = { ExternalSystemProxyApplication.class })
public class CucumberSpingConfiguration {}
