package com.gromenaware.corball.utils;

import org.openqa.selenium.By;

/**
 * Created by guillem on 14/04/15.
 */
public class Locators {
    public static By identifyLocationStrategy(String objectId) {
        By by = null;
        if (objectId.startsWith("/") || objectId.startsWith("(") || objectId.startsWith(".//*")) {
            by = By.xpath(objectId);
        } else if (objectId.startsWith("div") || objectId.startsWith("span") || objectId
            .startsWith("he") || objectId.startsWith("css")) {
            by = By.cssSelector(objectId);
        } else {
            by = By.id(objectId);
        }
        return by;
    }
}
