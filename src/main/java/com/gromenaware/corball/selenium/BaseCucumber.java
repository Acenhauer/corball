package com.gromenaware.corball.selenium;

import com.gromenaware.corball.saucelabs.*;
import com.gromenaware.corball.utils.PropertiesUtils;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import org.apache.logging.log4j.core.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;

import java.lang.reflect.Method;
import java.util.Properties;

/**
 * Created by guillem on 28/06/16.
 */
@Listeners({SauceOnDemandTestListener.class})
public class BaseCucumber extends AbstractTestNGCucumberTests implements SauceOnDemandSessionIdProvider, SauceOnDemandAuthenticationProvider {

    public static final Properties testProperties =
            PropertiesUtils.getProcessedTestProperties();
    public static final String host = testProperties.getProperty(PropertiesUtils.HOST);
    public static final String hub = testProperties.getProperty(PropertiesUtils.HUB);
    public SauceOnDemandAuthentication authentication =
            new SauceOnDemandAuthentication(SauceHubParser.getUserSaucelabs(hub),
                    SauceHubParser.getApikeySaucelabs(hub));
    public InheritableThreadLocal<String> sessionId = new InheritableThreadLocal<>();
    public InheritableThreadLocal<RemoteWebDriver> globalDriver = new InheritableThreadLocal<>();
    public InheritableThreadLocal<Logger> globalLogger = new InheritableThreadLocal<Logger>();
    public InheritableThreadLocal<BrowserCapabilities> globalBrowserCapabilities =
            new InheritableThreadLocal<BrowserCapabilities>();
    public static final int DRIVER_SELENIUM_TIMEOUT_MILISECONDS = 60000;
    public static final String browserName =
            testProperties.getProperty(PropertiesUtils.BROWSER);

    protected RemoteWebDriver driver() {
        return globalDriver.get();
    }

    protected Logger logger() {
        return globalLogger.get();
    }


    @BeforeTest
    public void setUp(Method method, Object[] testArguments) {
        StartWebDriver startWebDriver = new StartWebDriver();
        startWebDriver.startWebDriver(globalLogger,
                globalDriver,
                globalBrowserCapabilities,
                sessionId,
                method, hub, browserName,
                DRIVER_SELENIUM_TIMEOUT_MILISECONDS, host);
    }

    @AfterTest
    public void tearDown() {
        driver().quit();
    }

    @Override
    public SauceOnDemandAuthentication getAuthentication() {
        return authentication;
    }

    /**
     * @return the Sauce Job id for the current thread
     */
    public String getSessionId() {
        return sessionId.get();
    }
}