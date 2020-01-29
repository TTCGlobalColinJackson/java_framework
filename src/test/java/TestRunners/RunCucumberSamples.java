package TestRunners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Category;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import java.time.LocalDateTime;

import static helpers.GlobalValues.*;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        tags = {"not @ignore"},
        glue = {"stepDefinitions"},
        strict = true,
        monochrome = true,
        plugin  = {"pretty",
                "html:target/cucumber-html-reports",
                "json:target/cucumber-html-reports/cucumber.json",
                "junit:target/cucumber-html-reports/cucumber.xml",
                "rerun:target/cucumber-html-reports/rerun.txt",
                "de.monochromata.cucumber.report.PrettyReports:target"}
)
public class RunCucumberSamples {

    private static Category log = Logger.getLogger(RunCucumberSamples.class);

    @BeforeClass
    public static void startup(){
        System.setProperty("cucumber.reporting.config.testStartTime", LocalDateTime.now().toString());
        log.info(String.format("TTC Java Test Framework %s circa %s. For enquiries contact %s (%s)",
                release, releaseDate, codeOwner, codeOwnerEmail));
    }

    @AfterClass
    public static void teardown(){
        log.info(String.format("Test Started at %s and ended at %s",
                StringUtils.substringBefore(System.getProperty("cucumber.reporting.config.testStartTime"), "."),
                StringUtils.substringBefore(LocalDateTime.now().toString(), ".")));
    }
}
