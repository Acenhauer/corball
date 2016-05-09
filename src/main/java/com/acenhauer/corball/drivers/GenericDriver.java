package com.acenhauer.corball.drivers;

import com.acenhauer.corball.http.HttpClient;
import com.acenhauer.corball.utils.PropertiesUtils;
import com.acenhauer.corball.soap.SOAPClient;
import org.apache.log4j.Logger;

import java.util.Properties;

/**
 * Created by guillem on 15/02/16.
 */
public class GenericDriver {
    public static final Properties testProperties =
        PropertiesUtils.getProcessedTestProperties();
    public InheritableThreadLocal<HttpClient> globalHttpDriver =
        new InheritableThreadLocal<HttpClient>();
    public InheritableThreadLocal<SOAPClient> globalSoapDriver =
        new InheritableThreadLocal<SOAPClient>();
    public InheritableThreadLocal<Logger> globalLogger = new InheritableThreadLocal<Logger>();
    public String testName;
    public String testMethodName;

    public synchronized String getTestName() {
        return testName;
    }

    public synchronized void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestMethodName() {
        return testMethodName;
    }

    public void setTestMethodName(String testMethodName) {
        this.testMethodName = testMethodName;
    }

    protected HttpClient httpDriver() {
        return globalHttpDriver.get();
    }

    protected SOAPClient soapDriver() {
        return globalSoapDriver.get();
    }

    protected Logger logger() {
        return globalLogger.get();
    }
}
