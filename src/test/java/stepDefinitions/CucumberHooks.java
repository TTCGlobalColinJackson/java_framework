package stepDefinitions;

import helpers.Driver;
import helpers.GlobalValues;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Category;
import org.apache.log4j.Logger;

import java.time.LocalDateTime;

public class CucumberHooks implements GlobalValues {

    private static final Category log = Logger.getLogger(CucumberHooks.class);

    @Before
    public void beforeScenario(){
        LocalDateTime startTime = LocalDateTime.now();
        System.setProperty("cucumber.reporting.config.file", reportingConfigFile);
        System.setProperty("cucumber.reporting.config.scenarioStartTime", startTime.toString());
        Driver.getDriver();
    }

    @After
    public void afterScenario(Scenario scenario){
        Driver.quit();
        log.info(String.format("Scenario started at %s and ended at %s",
                StringUtils.substringBefore(System.getProperty("cucumber.reporting.config.scenarioStartTime"), "."),
                StringUtils.substringBefore(LocalDateTime.now().toString(), ".")));
    }

}
