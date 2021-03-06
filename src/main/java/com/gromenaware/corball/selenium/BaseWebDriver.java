package com.gromenaware.corball.selenium;

import com.gromenaware.corball.drivers.GenericSauceDriver;
import com.gromenaware.corball.utils.PropertiesUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Base Selenium Class to execute all the tests
 *
 * @author guillem
 */
public class BaseWebDriver extends GenericSauceDriver {

    public static final int DRIVER_SELENIUM_TIMEOUT_SECONDS = 30;
    public static final String browserName =
            testProperties.getProperty(PropertiesUtils.BROWSER);
    public static final String user = testProperties.getProperty(PropertiesUtils.USER);
    public static final String passwd = testProperties.getProperty(PropertiesUtils.PASSWD);
    public static final String startUrl = testProperties.getProperty(PropertiesUtils.HOST);
    public static RemoteWebDriverWait wait;

    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method, Object[] testArguments)
            throws IOException {
        StartWebDriver startWebDriver = new StartWebDriver();
        startWebDriver.startWebDriver(globalLogger,
                globalDriver,
                globalBrowserCapabilities,
                sessionId,
                method, hub, browserName,
                DRIVER_SELENIUM_TIMEOUT_SECONDS, host);
        wait = new RemoteWebDriverWait(globalDriver.get(), DRIVER_SELENIUM_TIMEOUT_SECONDS);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(Method method) {
        globalDriver.get().quit();
    }
}
