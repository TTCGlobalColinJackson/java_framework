package helpers;

public interface GlobalValues {

    // Release
    String release = "release/1.0";
    String releaseDate = "Jan 2020";
    String codeOwner = "Colin Jackson";
    String codeOwnerEmail = "colin.jackson@ttcglobal.com";

    // Browser
    String chromedriver = "browserhub/chromedriver.exe";
    boolean browserlessMode = false;
    String browserType = "chrome";
    String browserWidth = "1080";
    String browserHeight = "870";
    boolean browserStartMaximised = false;

    // Cucumber Reports
    String reportingConfigFile = "src/main/resources/prettyreports.properties";

    // BBC
    String BBCHomepageURL = "https://www.bbc.co.uk/";
    String TTCHomepageURL = "https://www.ttcglobal.com/";
    String GoogleHomepageURL = "https://www.google.com/";

}
