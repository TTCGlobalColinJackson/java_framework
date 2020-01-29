package stepDefinitions;

import helpers.Browser;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.log4j.Category;
import org.apache.log4j.Logger;
import views.BBCHomepage;

import static helpers.GlobalValues.BBCHomepageURL;

public class sampleSteps2 {

    private static final Category log = Logger.getLogger(sampleSteps2.class);

    @When("I open the BBC website")
    public void iOpenTheBBCWebsite() {
        Browser.navigateAndWait(BBCHomepageURL, 30);
    }

    @Then("I can see that the leaderboard is displayed")
    public void iCanSeeThatTheLeaderboardIsDisplayed() {
        BBCHomepage.shouldExist();
    }
}
