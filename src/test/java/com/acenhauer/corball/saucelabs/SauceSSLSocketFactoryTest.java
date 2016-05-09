package com.acenhauer.corball.saucelabs;

import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Custom SSLSocketFactory implementation which ensures that a valid SSL protocol is used.
 * <p>
 * Taken from http://stackoverflow.com/questions/1037590/which-cipher-suites-to-enable-for-ssl-socket/23365536#23365536
 *
 * @author Ross Rowe
 */
public class SauceSSLSocketFactoryTest {
    @Test
    public void testMethod() {
        assertTrue(true);
    }
}
