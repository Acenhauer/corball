package com.acenhauer.corball.selenium;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by guillem on 03/03/16.
 */
public class StartWebDriver {

    public StartWebDriver() {
    }

    public void startWebDriver(InheritableThreadLocal<Logger> globalLogger,
                               InheritableThreadLocal<RemoteWebDriver> globalDriver,
                               InheritableThreadLocal<BrowserCapabilities> globalBrowserCapabilities,
                               InheritableThreadLocal<String> sessionId,
                               Method method, String hub, String browserName,
                               int timeOut, String host) {
        globalLogger.set((Logger) LogManager.getLogger(BaseWebDriver.class));
        globalBrowserCapabilities.set(new BrowserCapabilities());
        RemoteWebDriver rwd = null;
        try {
            DesiredCapabilities caps = globalBrowserCapabilities.get()
                    .getDesiredCapabilitiesPerBrowser(browserName,
                            method.getName() + "-" + UUID.randomUUID());
            rwd = new RemoteWebDriver(new URL(hub), caps);
            sessionId.set(rwd.getSessionId().toString());
            globalLogger.get().info("Starting " + browserName + " using sessionId " + rwd.getSessionId().toString());
        } catch (MalformedURLException e) {
            globalLogger.get().info(e);
        }
        globalDriver.set(rwd);
        globalDriver.get().manage().timeouts()
                .pageLoadTimeout(timeOut, TimeUnit.MILLISECONDS);
        globalDriver.get().manage().timeouts()
                .setScriptTimeout(timeOut, TimeUnit.MILLISECONDS);
        globalDriver.get().manage().timeouts()
                .implicitlyWait(timeOut, TimeUnit.MILLISECONDS);
        globalDriver.get().manage().deleteAllCookies();
        globalDriver.get().manage().window().maximize();
        globalDriver.get().get(host);
        globalLogger.get().info("Starting URL: " + globalDriver.get().getCurrentUrl());
    }
}
