package com.acenhauer.corball.selenium;

import com.acenhauer.corball.drivers.GenericSauceDriverTest;
import com.acenhauer.corball.utils.PropertiesUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;

import static org.testng.Assert.assertTrue;

/**
 * Base Selenium Class to execute all the tests
 *
 * @author guillem
 */
public class BaseWebDriverTest {

    @Test
    public void testMethod() {
        assertTrue(true);
    }
}
