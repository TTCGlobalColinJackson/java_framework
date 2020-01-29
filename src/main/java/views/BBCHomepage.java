package views;

import helpers.Browser;
import org.apache.log4j.Category;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

import static helpers.Driver.getDriver;
import static org.hamcrest.MatcherAssert.assertThat;

public class BBCHomepage {

    private WebDriver driver;
    private static final Category log = Logger.getLogger(BBCHomepage.class);
    public BBCHomepage(){ driver = getDriver(); }

    public static void shouldExist(){
        By by = By.id("orb-header");
        Duration duration = Duration.ofSeconds(8);
        assertThat("The BBC Homepage should exist", Browser.elementShouldExist(by, duration));
    }


}
