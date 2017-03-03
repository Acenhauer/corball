package com.gromenaware.corball.selenium;

import com.gromenaware.corball.utils.Locators;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.*;

import java.util.Arrays;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Wraps RemoteWebDriverWait to be used in a more convenient way and adds functionality.
 *
 * @author guillem.hernandez
 */
public class RemoteWebDriverWait extends WebDriverWait {

    private static final Logger LOGGER = LogManager.getLogger(RemoteWebDriverWait.class);

    private final WebDriver driver;

    public RemoteWebDriverWait(WebDriver driver, long timeOutInSeconds) {
        super(driver, timeOutInSeconds);
        this.driver = driver;
    }

    public static boolean isVisible(WebDriver driver, String locator) {
        WebElement element = driver.findElement(Locators.identifyLocationStrategy(locator));
        return ExpectedConditions.visibilityOf(element) != null;
    }

    public static ExpectedCondition<Boolean> isElementPresent(final WebElement element) {
        ExpectedCondition<Boolean> elementPresent = new ExpectedCondition<Boolean>() {

            @Override
            public Boolean apply(WebDriver localDriver) {
                return ExpectedConditions.visibilityOf(element) != null;
            }
        };
        return elementPresent;
    }

    public static boolean isElementPresent(WebDriver driver, String locator) {
        WebElement element = driver.findElement(By.id(locator));
        return ExpectedConditions.visibilityOf(element) != null;
    }

    public void forElementPresent(WebElement element) {
        until(isElementPresent(element));
    }

    public void forElementPresent(By locator) {
        until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public void forTitle(String title) {
        until(ExpectedConditions.titleIs(title));
    }

    public void forTitleContaining(String titleSubstr) {
        until(ExpectedConditions.titleContains(titleSubstr));
    }

    public void forElementNotPresent(WebElement element) {
        until(not(isElementPresent(element)));
    }

    public void forAllElementsPresent(WebElement... elementsList) {
        until(areAllElementsPresent(elementsList));
    }

    public void forElementToBeSelected(WebElement element) {
        until(ExpectedConditions.elementToBeSelected(element));
    }

    public void forElementDisabled(WebElement element) {
        until(ExpectedConditions.stalenessOf(element));
    }

    public void forElementVisible(By locator) {
        forElementPresent(locator);
        until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void forElementVisible(WebElement element) {
        forElementPresent(element);
        until(ExpectedConditions.visibilityOf(element));
    }

    public void forElementNotVisible(By locator) {
        until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public void forElementToBeClickable(By locator) {
        until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void forElementToBeGone(WebElement element) {
        until(ExpectedConditions.stalenessOf(element));
    }

    public void forTextPresent(By locator, String text) {
        until(ExpectedConditions.textToBePresentInElement(locator, text));
    }

    public void forTextPresentInElementValue(By locator, String text) {
        until(ExpectedConditions.textToBePresentInElementValue(locator, text));
    }

    public void forFrameAvailableAndSwitchToIt(String frameLocator) {
        until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
    }

    public Boolean forAsyncAttribute(WebElement e, String textValue, long milliseconds,
                                     int retries) {
        // return true when the element is present
        // Up to n retries times
        for (int i = 0; i < retries; i++) {
            // Check whether our element is there yet
            if (e.getAttribute(textValue) != null) {
                return Boolean.valueOf(e.getAttribute(textValue));
            }
            pause(milliseconds);
        }
        return Boolean.valueOf(null);
    }

    public void forPageAndAjaxToLoad() {
        until(isPageAndAjaxLoaded());
    }

    public ExpectedCondition<Boolean> areAllElementsPresent(final WebElement... elementsList) {
        ExpectedCondition<Boolean> elementsPresent = new ExpectedCondition<Boolean>() {

            @Override
            public Boolean apply(WebDriver localDriver) {
                return ExpectedConditions.visibilityOfAllElements(Arrays.asList(elementsList))
                        != null;
            }
        };
        return elementsPresent;
    }

    public ExpectedCondition<Boolean> not(final ExpectedCondition<?> toInvert) {
        return new ExpectedCondition<Boolean>() {

            @Override
            public Boolean apply(WebDriver localDriver) {
                try {
                    Object result = toInvert.apply(driver);
                    return (result == null || result == Boolean.FALSE);
                } catch (Exception e) {
                    LOGGER.info(e);
                    return true;
                }
            }
        };
    }

    public ExpectedCondition<Boolean> isPageAndAjaxLoaded() {
        ExpectedCondition<Boolean> pageLoaded = new ExpectedCondition<Boolean>() {

            @Override
            public Boolean apply(WebDriver localDriver) {
                return ((JavascriptExecutor) localDriver)
                        .executeScript("return document.readyState").equals("complete");
            }
        };
        return pageLoaded;
    }

    public void pause(long milliseconds) {
        try {
            Sleeper.SYSTEM_SLEEPER.sleep(new Duration(milliseconds, MILLISECONDS));
        } catch (InterruptedException ie) {
            LOGGER.info("Waiting finished during " + milliseconds + " milliseconds ");
        }
    }

    // Leaving this here just in case we want to add extra actions in the future.
    @Override
    protected RuntimeException timeoutException(String message, Throwable lastException) {
        return super.timeoutException(message, lastException);
    }
}
