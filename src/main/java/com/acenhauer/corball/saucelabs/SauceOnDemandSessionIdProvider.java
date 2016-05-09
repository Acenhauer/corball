package com.acenhauer.corball.saucelabs;

/**
 * Interface that should be implemented by classes using the TestNG or JUnit helper classes.
 * @author see for original
 * @author Ross Rowe - updated documentation
 */
public interface SauceOnDemandSessionIdProvider {

    /**
     * Return the session id for the SeleniumRC/WebDriver instance - this equates to the Sauce OnDemand
     * Job id.
     * @return string representing the session id for the SeleniumRC/WebDriver instance
     */
    String getSessionId();
}
