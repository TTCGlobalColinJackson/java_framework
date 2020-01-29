package helpers;

import com.google.common.base.Function;
import io.cucumber.java.Scenario;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static helpers.Driver.getDriver;
import static org.hamcrest.MatcherAssert.assertThat;

public class Browser {

	private final static Logger log = Logger.getLogger(Browser.class);

	public static void waitForElement(By by) {
		waitForElement(by, 60);
	}

	public static void waitForElement(By by, int timeoutInSeconds) {
		Duration timeout = Duration.ofSeconds(timeoutInSeconds);
		Duration pollingPeriod = Duration.ofSeconds(1);
		Wait<WebDriver> wait = new FluentWait<>(getDriver()).withTimeout(timeout)
				.pollingEvery(pollingPeriod).ignoring(NoSuchElementException.class);
		WebElement webElement = wait.until((Function<WebDriver, WebElement>) driver -> {
			driver = getDriver();
			return driver.findElement(by);
		});
	}

	public static void waitForElement(WebElement element) {
		Duration timeout = Duration.ofSeconds(4);
		Duration pollingPeriod = Duration.ofSeconds(1);
		Wait<WebDriver> wait = new FluentWait<>(getDriver()).withTimeout(timeout)
				.pollingEvery(pollingPeriod).ignoring(NoSuchElementException.class);
		WebElement webElement = wait.until((Function<WebDriver, WebElement>) driver -> element);
	}

	public static void waitForElementVisible(By by) {
		Duration timeout = Duration.ofSeconds(4);
		Duration pollingPeriod = Duration.ofSeconds(1);
		Wait<WebDriver> wait = new FluentWait<>(getDriver()).withTimeout(timeout)
				.pollingEvery(pollingPeriod).ignoring(NoSuchElementException.class);
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}

	public static void waitForElementVisible(WebElement element) {
		waitForElementVisible(element, 30);
	}

	public static void waitForElementVisible(WebElement element, int timeoutInSeconds) {
		Duration timeout = Duration.ofSeconds(timeoutInSeconds);
		Duration pollingPeriod = Duration.ofSeconds(1);
		Wait<WebDriver> wait = new FluentWait<>(getDriver()).withTimeout(timeout)
				.pollingEvery(pollingPeriod).ignoring(NoSuchElementException.class);
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public static void waitForElementClickable(WebElement element) {
		Duration timeout = Duration.ofSeconds(4);
		Duration pollingPeriod = Duration.ofSeconds(1);
		Wait<WebDriver> wait = new FluentWait<>(getDriver()).withTimeout(timeout)
				.pollingEvery(pollingPeriod).ignoring(NoSuchElementException.class);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public static void waitForElementClickable(By by) {
		waitForElementClickable(by, 60);
	}

	public static void waitForElementClickable(By by, int timeoutInSeconds) {
		Duration timeout = Duration.ofSeconds(timeoutInSeconds);
		Duration pollingPeriod = Duration.ofSeconds(1);
		Wait<WebDriver> wait = new FluentWait<>(getDriver()).withTimeout(timeout)
				.pollingEvery(pollingPeriod).ignoring(NoSuchElementException.class);
		wait.until(ExpectedConditions.elementToBeClickable(by));
	}

	public static void waitForPageToLoad() {
		Duration timeout = Duration.ofSeconds(4);
		(new WebDriverWait(getDriver(), timeout)).until((ExpectedCondition<Boolean>) d -> (((JavascriptExecutor) getDriver())
				.executeScript("return document.readyState").equals("complete")));
	}

	public static void waitForProcessing() {
		waitForProcessing(30);
	}

	// waiting for the javascript process for maximum duration
	public static void waitForProcessing(int timeoutSeconds) {

		assertThat(String.format("Timeout must be greater than '%s'", timeoutSeconds), timeoutSeconds > 0);

		try {
			Boolean isJqueryUsed = (Boolean) ((JavascriptExecutor) getDriver())
					.executeScript("return (typeof(jQuery) != 'undefined')");
			if (isJqueryUsed) {
				for (int i = 0; i < timeoutSeconds; i++) {
					// JavaScript test to verify jQuery is active or not
					Boolean ajaxIsComplete = (Boolean) (((JavascriptExecutor) getDriver())
							.executeScript("return jQuery.active == 0"));
					if (ajaxIsComplete)
						break;
					Thread.sleep(1000);
				}
			}
		} catch (InterruptedException e) {
			log.error("Failed to wait for processing", e);
			e.printStackTrace();
		}
	}

	public static boolean elementShouldExist(By by, Duration waitDuration) {
		Duration pollingPeriod = Duration.ofSeconds(1);
		try {
			Wait<WebDriver> wait = new FluentWait<>(getDriver()).withTimeout(waitDuration)
					.pollingEvery(pollingPeriod).ignoring(NoSuchElementException.class);
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public static void navigate(String url) {
		getDriver().get(url);
	}

	public static void navigateAndWait(String url, int timeoutInSeconds) {
		getDriver().get(url);
		Duration timeout = Duration.ofSeconds(timeoutInSeconds);
		Duration pollingPeriod = Duration.ofSeconds(1);
		Wait<WebDriver> wait = new FluentWait<>(getDriver()).withTimeout(timeout)
				.pollingEvery(pollingPeriod).ignoring(NoSuchElementException.class);
		wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
	}

	public static void navigateAndWait(String url) {
		navigateAndWait(url, 30);
	}

	// cucumber screenshot function take screenshot when test case failed
	public void takeScreenShot(Scenario scenario) {
		try {
			if (scenario.isFailed()) {
				final byte[] screenshot = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
				scenario.embed(screenshot, "image/png", "screenshot");
				log.debug("scenario failed capture a screenshot");
			}
		} catch (Exception e) {
			log.error("Failed to take a screenshot", e);
			e.printStackTrace();
		}
	}

	public static boolean isElementPresent(By by) {
		try {
			getDriver().findElement(by);
			return true;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			log.error(e);
			return false;
		}
	}

	public static boolean isElementPresent(By by, int timeOutInSeconds) {
		getDriver().manage().timeouts().implicitlyWait(timeOutInSeconds, TimeUnit.SECONDS);
		boolean returnVal = true;
		try{
			getDriver().findElement(by);
		} catch (NoSuchElementException e){
			returnVal = false;
		} finally {
			getDriver().manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		}
		return returnVal;
	}

	public static boolean isChildElementPresent(By by, By childBy) {
		try {
			getDriver().findElement(by).findElement(childBy);
			return true;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			log.error(e);
			return false;
		}
	}

	public boolean isElementVisible(By by) {
		return getDriver().findElement(by).isDisplayed();
	}

	public static void clickElement(By by, int timeOutInSeconds) {
		waitForElementClickable(by, timeOutInSeconds);
		try {
			getDriver().findElement(by).click();
		}
		catch(WebDriverException e){
			getDriver().findElement(by).sendKeys(Keys.ARROW_RIGHT);
			getDriver().findElement(by).click();
		}
		waitForProcessing(timeOutInSeconds);
	}

	public static void clickElement(By by) {
		clickElement(by, 60);
	}

	public static void clickElement(WebElement element) {
		Duration timeout = Duration.ofSeconds(4);
		Duration pollingPeriod = Duration.ofSeconds(1);
		Wait<WebDriver> wait = new FluentWait<>(getDriver()).withTimeout(timeout)
				.pollingEvery(pollingPeriod).ignoring(NoSuchElementException.class);
		wait.until(ExpectedConditions.elementToBeClickable(element));
		try {
			element.click();
		}
		catch(WebDriverException e){
			element.sendKeys(Keys.ARROW_RIGHT);
			element.click();
		}
		waitForProcessing();
	}

	static public void clickElementFromList(By by, int index) {
		List<WebElement> elements = getDriver().findElements(by);
		elements.get(index).click();
	}

	// For elements that need repeated clicks
	public static void retryClick(By checkElement, By clickTarget) {
		getDriver().findElement(clickTarget).click();
		while (!isElementPresent(checkElement))
			getDriver().findElement(clickTarget).click();
	}

	static public String getTitle() {
		return getDriver().getTitle();
	}

	// click element using javascript executor
	static void jsClickElement(By by){
	 WebElement element = getDriver().findElement(by);
     JavascriptExecutor js = (JavascriptExecutor) getDriver();
     js.executeScript("arguments[0].click()", element);
	}

	static public void scrollTo(WebElement element) throws InterruptedException {
		((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
		Thread.sleep(2000);
	}

	static public void scrollUpTo(WebElement element) throws InterruptedException {
		((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(false);", element);
		Thread.sleep(2000);
	}

	static public void scrollUpTo(By by) throws InterruptedException {
		scrollUpTo(getDriver().findElement(by));
	}

	static public void scrollTo(By by) throws InterruptedException {
 		if(elementExists(by)){
			((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", getDriver().findElement(by));
			Thread.sleep(500);
		}
	}

	static public Boolean elementExists(By by){
		return getDriver().findElements(by).size() > 0;
	}

	// Use this when the Selenium click() fails due to overlay problems
	static public void cautiousClick(By by) throws InterruptedException {
		JavascriptExecutor executor = (JavascriptExecutor) getDriver();
		waitForProcessing();
		executor.executeScript("arguments[0].scrollIntoView();", getDriver().findElement(by));
		Thread.sleep(500);
		waitForElementClickable(by);
		executor.executeScript("arguments[0].click();", getDriver().findElement(by));
	}

}
