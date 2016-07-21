package com.acenhauer.corball.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertiesUtils {

    public static final String PACKAGENAME = "packageName";
    public static final String PACKAGELINK = "packageLink";
    public static final String HOST = "host";
    public static final String BROWSER = "browser";
    public static final String HUB = "hub";
    public static final String USER = "user";
    public static final String PASSWD = "passwd";
    public static final String FORM = "form";
    public static final String APIKEY = "apikey";
    public static final String SHAREDSECRET = "sharedsecret";
    public static final String DEVICE = "device";
    public static final String PLATFORMVERSION = "platformversion";
    public static final String APPABSOLUTEPATH = "apkAbsolutePath";
    public static final String SAUCE = "sauce";

    /**
     * Loads a properties file using namespace as file name. If the file is not found returns an empty properties file.
     *
     * @return Properties read from the file
     */
    public static Properties loadResourceProperties() {
        Properties prop = new Properties();
        try {
            prop.load(new InputStreamReader(PropertiesUtils.class.getClassLoader()
                .getResourceAsStream("config.properties")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }

    /**
     * There are two levels for properties: system properties (command-line) with higher preference and project-specific, with lower preference.
     *
     * @param resourceProperties the properties to be overriden with system properties
     * @param systemProperties   the properties to override
     */
    public static void overrideProperties(Properties resourceProperties,
        Properties systemProperties) {
        if (resourceProperties != null) {
            for (Object keyObject : resourceProperties.keySet()) {
                String key = (String) keyObject;
                if (systemProperties.containsKey(key)) {
                    resourceProperties.setProperty(key, systemProperties.getProperty(key));
                }
            }
        }
    }

    /**
     * Returns the properties overridden with system (command-line) properties.
     *
     * @return the properties overridden with system (command-line) properties.
     */
    public static Properties getProcessedTestProperties() {
        Properties resourceProperties = loadResourceProperties();
        Properties systemProperties = System.getProperties();
        overrideProperties(resourceProperties, systemProperties);
        return resourceProperties;
    }
}
