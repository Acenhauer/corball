package com.acenhauer.corball.saucelabs;

import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Test Listener that providers helper logic for TestNG tests.  Upon startup, the class
 * will store any SELENIUM_* environment variables (typically set by a Sauce OnDemand CI
 * plugin) as system parameters, so that they can be retrieved by tests as parameters.
 * <p>
 * TODO how to specify whether to download log/video?
 *
 * @author Ross Rowe
 */
public class SauceOnDemandTestListenerTest {
    @Test
    public void testMethod() {
        assertTrue(true);
    }
}
