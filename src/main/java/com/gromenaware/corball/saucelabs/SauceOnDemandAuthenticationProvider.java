package com.gromenaware.corball.saucelabs;


/**
 * Marker interface which should be implemented by Tests that instantiate {@link SauceOnDemandAuthentication}.  This
 * interface is referenced by {@link SauceOnDemandTestListener} in order to construct the
 * instance using the authentication specified for the specific test.
 *
 * @author Ross Rowe
 */
public interface SauceOnDemandAuthenticationProvider {
    /**
     * @return the {@link SauceOnDemandAuthentication} instance for a specific test.
     */
    SauceOnDemandAuthentication getAuthentication();
}
