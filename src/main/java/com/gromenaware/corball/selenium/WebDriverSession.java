package com.gromenaware.corball.selenium;

/**
 * Created by guillem on 30/01/15.
 */
public class WebDriverSession {

    private String sessionId;

    public WebDriverSession(String sessionId) {
        this.sessionId = sessionId;
    }

    public WebDriverSession() {

    }

    public String getWebDriverSession() {
        return this.sessionId;
    }

    public synchronized void setWebDriverSession(String sessionId) {
        this.sessionId = sessionId;
    }


}
