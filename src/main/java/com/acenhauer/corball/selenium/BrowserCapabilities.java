package com.acenhauer.corball.selenium;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Platform;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Created by guillem on 15/04/15.
 */
public class BrowserCapabilities {
    public BrowserCapabilities() {
    }

    protected static FirefoxProfile getFirefoxProfile() {
        final int firefoxBrowserProfileWait = BaseWebDriver.DRIVER_SELENIUM_TIMEOUT_SECONDS;
        FirefoxProfile firefoxProfile = new FirefoxProfile();
        // Browser Timeouts to handle async scripts.
        firefoxProfile.setPreference("dom.max_chrome_script_run_time", firefoxBrowserProfileWait);
        firefoxProfile.setPreference("setTimeoutInSeconds", firefoxBrowserProfileWait);
        firefoxProfile.setPreference("dom.max_script_run_time", firefoxBrowserProfileWait);
        // Add pop up handling functionality.
        firefoxProfile.setPreference("dom.popup_allowed_events",
                "change click dblclick mouseup reset submit");
        firefoxProfile.setPreference("dom.popup_maximum", 0);
        firefoxProfile.setPreference("browser.xul.error_pages.enabled", false);
        firefoxProfile.setPreference("handlers.useragent.extra.firefox", "Firefox");
        firefoxProfile.setEnableNativeEvents(true);
        firefoxProfile.setPreference("app.update.enabled", false);
        firefoxProfile.setPreference("dom.disable_open_during_load", false);
        firefoxProfile.setPreference("browser.frames.enabled", true);
        firefoxProfile.setPreference("browser.popups.showPopupBlocker", true);
        firefoxProfile.setPreference("privacy.popups.showBrowserMessage", false);
        firefoxProfile.setPreference("privacy.popups.usecustom", true);
        firefoxProfile.setPreference("privacy.popups.firstTime", true);
        firefoxProfile.setPreference("privacy.popup.policy", 1);
        firefoxProfile.setPreference("privacy.popups.disable_from_plugins", 2);
        firefoxProfile.setAcceptUntrustedCertificates(true);
        firefoxProfile.setAssumeUntrustedCertificateIssuer(true);
        return firefoxProfile;
    }

    protected static Map<String, String> getChromePrefs() {
        Map<String, String> prefs = new HashMap<String, String>();
        prefs.put("download.prompt_for_download", "false");
        return prefs;
    }

    protected static ChromeOptions getChromeOptions() {
        ChromeOptions ops = new ChromeOptions();
        ops.addArguments("start-maximized");
        return ops;
    }

    public DesiredCapabilities getDesiredCapabilitiesPerBrowser(String browser, String testName) {
        DesiredCapabilities caps = new DesiredCapabilities();
        if (browser.equalsIgnoreCase("unset")) {
            caps = DesiredCapabilities.firefox();
            caps.setCapability("id", testName);
            caps.setCapability("name", testName);
            caps.setCapability(FirefoxDriver.PROFILE, getFirefoxProfile());
            return caps;
        } else {
            if (browser.equalsIgnoreCase("FIREFOX")) {
                caps = DesiredCapabilities.firefox();
                caps.setCapability("id", testName);
                caps.setCapability("name", testName);
                caps.setCapability(FirefoxDriver.PROFILE, getFirefoxProfile());
                caps.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR,
                        UnexpectedAlertBehaviour.ACCEPT);
                return caps;
            } else if (browser.equalsIgnoreCase("CHROME")) {
                caps = DesiredCapabilities.chrome();
                caps.setCapability("id", testName);
                caps.setCapability("name", testName);
                caps.setCapability("chrome.prefs", getChromePrefs());
                caps.setCapability(ChromeOptions.CAPABILITY, getChromeOptions());
                return caps;
            } else if (browser.equalsIgnoreCase("IE")) {
                caps = DesiredCapabilities.internetExplorer();
                caps.setCapability("id", testName);
                caps.setCapability("name", testName);
                caps.setCapability(
                        InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                caps.setJavascriptEnabled(true);
                caps.setCapability("nativeEvents", true);
                return caps;
            } else if (browser.equalsIgnoreCase("HTMLUNIT")) {
                caps = DesiredCapabilities.htmlUnit();
                caps.setCapability("id", testName);
                caps.setCapability("name", testName);
                return caps;
            } else if (browser.equalsIgnoreCase("HTMLUNITJS")) {
                caps = DesiredCapabilities.htmlUnitWithJs();
                caps.setCapability("id", testName);
                caps.setCapability("name", testName);
                return caps;
            } else if (browser.equalsIgnoreCase("PHANTOMJS")) {
                caps = DesiredCapabilities.phantomjs();
                caps.setCapability("id", testName);
                caps.setCapability("name", testName);
                return caps;
            } else if (browser.equalsIgnoreCase("SAFARI")) {
                caps = DesiredCapabilities.safari();
                caps.setCapability("id", testName);
                caps.setCapability("name", testName);
                caps.setCapability("platform", "Windows 7");
                caps.setCapability("version", "5");
                return caps;
            } else if (browser.equalsIgnoreCase("OPERA")) {
                caps = DesiredCapabilities.opera();
                caps.setCapability("id", testName);
                caps.setCapability("name", testName);
                caps.setCapability("platform", "Windows 7");
                caps.setCapability("version", "11");
                return caps;
            } else if (browser.equalsIgnoreCase("IE6")) {
                caps = DesiredCapabilities.internetExplorer();
                caps.setCapability(CapabilityType.BROWSER_NAME, "iexplore");
                caps.setCapability("id", testName);
                caps.setCapability("name", testName);
                caps.setCapability("platform", Platform.XP);
                caps.setCapability("version", "6");
                return caps;
            } else if (browser.equalsIgnoreCase("IE7")) {
                caps = DesiredCapabilities.internetExplorer();
                caps.setCapability(CapabilityType.BROWSER_NAME, "iexplore");
                caps.setCapability("id", testName);
                caps.setCapability("name", testName);
                caps.setCapability("version", "7");
                return caps;
            } else if (browser.equalsIgnoreCase("IE8")) {
                caps = DesiredCapabilities.internetExplorer();
                caps.setCapability(CapabilityType.BROWSER_NAME, "iexplore");
                caps.setCapability("id", testName);
                caps.setCapability("name", testName);
                caps.setCapability("version", "8");
                return caps;
            } else if (browser.equalsIgnoreCase("IE9")) {
                caps = DesiredCapabilities.internetExplorer();
                caps.setCapability(CapabilityType.BROWSER_NAME, "iexplore");
                caps.setCapability("id", testName);
                caps.setCapability("name", testName);
                caps.setCapability("platform", "Windows 7");
                caps.setCapability("version", "9");
                return caps;
            } else if (browser.equalsIgnoreCase("IE10")) {
                caps = DesiredCapabilities.internetExplorer();
                caps.setCapability(CapabilityType.BROWSER_NAME, "iexplore");
                caps.setCapability("id", testName);
                caps.setCapability("name", testName);
                caps.setCapability("platform", "Windows 7");
                caps.setCapability("version", "10");
                return caps;
            } else if (browser.equalsIgnoreCase("IE11")) {
                caps = DesiredCapabilities.internetExplorer();
                caps.setCapability(CapabilityType.BROWSER_NAME, "iexplore");
                caps.setCapability("id", testName);
                caps.setCapability("name", testName);
                caps.setCapability("platform", "Windows 7");
                caps.setCapability("version", "11");
                return caps;
            } else if (browser.equalsIgnoreCase("EDGE")) {
                caps = DesiredCapabilities.edge();
                caps.setCapability("id", testName);
                caps.setCapability("name", testName);
                caps.setCapability("platform", "Windows 10");
                caps.setCapability("version", "20.10240");
                return caps;
            }
        }
        return caps;
    }
}
