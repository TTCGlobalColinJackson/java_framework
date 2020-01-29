package stepDefinitions;

import helpers.Browser;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.log4j.Category;
import org.apache.log4j.Logger;
import views.BBCHomepage;

import static helpers.GlobalValues.BBCHomepageURL;

public class moreSampleSteps {

    private static final Category log = Logger.getLogger(moreSampleSteps.class);

    @When("I open the BBC website")
    public void iOpenTheBBCWebsite() {
        Browser.navigateAndWait(BBCHomepageURL, 30);
    }

    @Then("I can see that the leaderboard is displayed")
    public void iCanSeeThatTheLeaderboardIsDisplayed() {
        BBCHomepage.shouldExist();
    }
}
