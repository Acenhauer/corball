package com.acenhauer.corball.http;

import com.acenhauer.corball.drivers.GenericDriver;
import com.acenhauer.corball.utils.PropertiesUtils;
import org.apache.logging.log4j.LogManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

/**
 * Created by guillem.hernandez on 12/12/2014.
 */

public class BaseHttp extends GenericDriver {

    public static final String endPoint = testProperties.getProperty(PropertiesUtils.HOST);
    public static final String apiKey = testProperties.getProperty(PropertiesUtils.APIKEY);
    public static final String sharedSecret =
            testProperties.getProperty(PropertiesUtils.SHAREDSECRET);

    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method) {
        globalHttpDriver.set(new HttpClient());
        globalLogger.set(LogManager.getLogger(BaseHttp.class));
        Test tNGAnn = method.getAnnotation(org.testng.annotations.Test.class);
        if (tNGAnn != null) setTestName(tNGAnn.testName());
        setTestMethodName(method.getName());
        if (getTestName().isEmpty()) setTestName(method.getName());

        globalLogger.get().info(" Starting execution for testcase " + getTestMethodName() + "> " + getTestName());
    }

    @AfterMethod(alwaysRun = true)
    protected void teardown() {
        globalLogger.get().info(" Finished execution for testcase " + getTestMethodName() + "> " + getTestName());
    }

}
