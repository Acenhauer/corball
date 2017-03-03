package com.gromenaware.corball.saucelabs;


/**
 * Simple Java API that invokes the Sauce REST API.
 *
 * @author SauceLabs
 * @author guillem.hernandez (Adapted for the framework)
 */
public class SauceHubParser {

    protected String username;
    protected String accessKey;

    public SauceHubParser(String username, String accessKey) {
        this.username = username;
        this.accessKey = accessKey;
    }

    public static String getUserSaucelabs(String hub) {
        return splitTwoCharacters(hub, "//", ":");
    }

    public static String getApikeySaucelabs(String hub) {
        String[] firstpart = hub.split("@");
        String[] secondpart = firstpart[0].split(":");
        return secondpart[2];
    }

    public static String splitTwoCharacters(String part, String param1, String param2) {
        String[] firstpart = part.split(param1);
        String[] secondpart = firstpart[1].split(param2);
        return secondpart[0];
    }
}
