package stepDefinitions;

import helpers.Browser;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.log4j.Category;
import org.apache.log4j.Logger;
import views.GoogleHomepage;
import views.TTCHomepage;

import static helpers.GlobalValues.GoogleHomepageURL;
import static helpers.GlobalValues.TTCHomepageURL;

public class sampleSteps {

    private static final Category log = Logger.getLogger(sampleSteps.class);

    @Given("I am on the Google homepage")
    public void iAmOnTheGoogleHomepage() {
        Browser.navigateAndWait(GoogleHomepageURL, 30);
        GoogleHomepage.shouldExist();
    }

    @When("I open the TTC website")
    public void iOpenTheTTCWebsite() {
        Browser.navigateAndWait(TTCHomepageURL, 30);
    }

    @Then("I can see that the menu is displayed")
    public void iCanSeeThatTheMenuIsDisplayed() {
        TTCHomepage.shouldExist();
    }
}
