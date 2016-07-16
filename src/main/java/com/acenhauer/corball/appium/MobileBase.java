package com.acenhauer.corball.appium;

import com.acenhauer.corball.drivers.GenericSauceDriver;
import com.acenhauer.corball.utils.PropertiesUtils;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Created by guillemhs on 2015-11-29.
 */
public class MobileBase extends GenericSauceDriver {
    public static final String device = testProperties.getProperty(PropertiesUtils.DEVICE);
    public static final String platformVersion = testProperties.getProperty(PropertiesUtils.PLATFORMVERSION);

    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method) throws MalformedURLException {
        // switch between different browsers, e.g. iOS Safari or Android Chrome
        // let's use the os name to differentiate, because we only use default browser in that os
        if (device != null && device.equalsIgnoreCase("Android")) {
            DesiredCapabilities caps = DesiredCapabilities.android();
            caps.setCapability("appiumVersion", "1.4.16");
            caps.setCapability("deviceName", "Android Emulator");
            caps.setCapability("deviceType", "phone");
            caps.setCapability("deviceOrientation", "portrait");
            caps.setCapability("browserName", "Browser");
            caps.setCapability("platformVersion", platformVersion);
            caps.setCapability("platformName", "Android");
            caps.setCapability("id", method.getName());
            caps.setCapability("name", method.getName());
            RemoteWebDriver driver = new AndroidDriver(new URL(hub), caps);
            sessionId.set(driver.getSessionId().toString());
            globalDriver.set(driver);
            globalDriver.get().manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        } else {
            DesiredCapabilities caps = DesiredCapabilities.iphone();
            caps.setCapability("appiumVersion", "1.4.16");
            caps.setCapability("deviceName", "iPhone 6");
            caps.setCapability("deviceOrientation", "portrait");
            caps.setCapability("platformVersion", platformVersion);
            caps.setCapability("platformName", "iOS");
            caps.setCapability("browserName", "Safari");
            caps.setCapability("id", method.getName());
            caps.setCapability("name", method.getName());
            RemoteWebDriver driver = new IOSDriver(new URL(hub), caps);
            sessionId.set(driver.getSessionId().toString());
            globalDriver.set(driver);
            globalDriver.get().manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        }
    }
}
