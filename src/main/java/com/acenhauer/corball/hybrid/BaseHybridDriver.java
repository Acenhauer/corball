package com.acenhauer.corball.hybrid;

import com.acenhauer.corball.drivers.GenericSauceDriver;
import com.acenhauer.corball.selenium.BrowserCapabilities;
import com.acenhauer.corball.selenium.RemoteWebDriverWait;
import com.acenhauer.corball.utils.PropertiesUtils;
import com.acenhauer.corball.soap.SOAPClient;
import org.apache.log4j.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
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


    @BeforeMethod(alwaysRun = true) public void setUp(Method method) {
        globalLogger.set(Logger.getLogger(BaseHybridDriver.class));
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
        wait = new RemoteWebDriverWait(globalDriver.get(), DEFAULTTIMEOUTSEC);
        globalDriver.get().manage().window().maximize();
        globalDriver.get().get(host);
        globalXMLDriver.set(new SOAPClient());
        globalLogger.get().info("Starting URL: " + globalDriver.get().getCurrentUrl());
    }

    @AfterMethod(alwaysRun = true) protected void teardown(ITestResult tr) {
        globalDriver.get().quit();
        if (tr.isSuccess()) {
            logger().info(getSessionId() + " PASSED! ");
        } else {
            logger().info(getSessionId() + " FAILED! ");
        }
        globalLogger.get().info("Finished execution for testcase " + getSessionId());
    }
}
