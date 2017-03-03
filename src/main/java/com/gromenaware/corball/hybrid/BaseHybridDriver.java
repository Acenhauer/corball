package com.gromenaware.corball.hybrid;

import com.gromenaware.corball.drivers.GenericSauceDriver;
import com.gromenaware.corball.selenium.BrowserCapabilities;
import com.gromenaware.corball.soap.SOAPClient;
import com.gromenaware.corball.utils.PropertiesUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Base Selenium Class to execute all the tests
 *
 * @author guillem
 */

public class BaseHybridDriver extends GenericSauceDriver {
    // Selenium configuration
    public static final Integer DEFAULTTIMEOUTSEC = 30000; // enough time to load
    public static final Long DRIVER_SELENIUM_TIMEOUT_MILISECONDS = new Long(20000);
    public static final String browserName =
            testProperties.getProperty(PropertiesUtils.BROWSER);
    public static final String form = testProperties.getProperty(PropertiesUtils.FORM);
    public static final String startUrl = testProperties.getProperty(PropertiesUtils.HOST);

    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method) {
        globalLogger.set((Logger) LogManager.getLogger(BaseHybridDriver.class));
        globalBrowserCapabilities.set(new BrowserCapabilities());
        RemoteWebDriver rwd = null;
        try {
            DesiredCapabilities caps = globalBrowserCapabilities.get()
                    .getDesiredCapabilitiesPerBrowser(browserName,
                            method.getName() + "-" + UUID.randomUUID());
            rwd = new RemoteWebDriver(new URL(hub), caps);
            sessionId.set(rwd.getSessionId().toString());
            globalLogger.get()
                    .info("Starting " + browserName + " using sessionId " + getSessionId());
        } catch (MalformedURLException e) {
            logger().info(e);
        }
        globalDriver.set(rwd);
        globalDriver.get().manage().timeouts()
                .pageLoadTimeout(DRIVER_SELENIUM_TIMEOUT_MILISECONDS, TimeUnit.MILLISECONDS);
        globalDriver.get().manage().timeouts()
                .setScriptTimeout(DRIVER_SELENIUM_TIMEOUT_MILISECONDS, TimeUnit.MILLISECONDS);
        globalDriver.get().manage().timeouts()
                .implicitlyWait(DRIVER_SELENIUM_TIMEOUT_MILISECONDS, TimeUnit.MILLISECONDS);
        globalDriver.get().manage().deleteAllCookies();
        globalDriver.get().manage().window().maximize();
        globalDriver.get().get(host);
        globalXMLDriver.set(new SOAPClient());
        globalLogger.get().info("Starting URL: " + globalDriver.get().getCurrentUrl());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(Method method) {
        globalDriver.get().quit();
    }
}
