package com.gromenaware.corball.soap;

import com.gromenaware.corball.drivers.GenericDriver;
import com.gromenaware.corball.utils.PropertiesUtils;
import org.apache.logging.log4j.LogManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;
import java.util.Properties;

/**
 * Created by guillem.hernandez on 12/12/2014.
 */

public class BaseSoap extends GenericDriver {
    public static final Properties testProperties =
            PropertiesUtils.getProcessedTestProperties();
    public static final String endPoint = testProperties.getProperty(PropertiesUtils.HOST);
    public static final String user = testProperties.getProperty(PropertiesUtils.USER);
    public static final String passwd = testProperties.getProperty(PropertiesUtils.PASSWD);

    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method) {
        globalSoapDriver.set(new SOAPClient());
        globalLogger.set(LogManager.getLogger(BaseSoap.class));
    }

    @AfterMethod(alwaysRun = true)
    protected void teardown() {
        globalLogger.get().info("Finished execution for testcase ");
    }
}
