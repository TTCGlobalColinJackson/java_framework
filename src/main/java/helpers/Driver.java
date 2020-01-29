package helpers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ThreadGuard;

public class Driver {

    public static WebDriver driver = null;

    public Driver(){}

    public static WebDriver getDriver() {
        if(null == driver){
            driver = ThreadGuard.protect(new ChromeDriver());
            return driver;
        }else {
            return driver;
        }
    }

    public static void quit() {
        driver.quit();
        driver = null;
    }
}
