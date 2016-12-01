package com.test.base;


import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class WebDriverBase {
    private static final Logger LOG = LoggerFactory.getLogger(WebDriverBase.class);
    protected static WebDriver driver;

    private static String testServer;
    private static String testrailServer;
    private static String productName; //Static variables in this class will persist across scenarios.
    private static HashMap<String, Object> generalData; // instead of using system.SetProperty.



    public static WebDriver getDriver(){

       if(driver == null){
            driver = SeleniumBase.getDriver();
            String url = SeleniumBase.getURL();
            driver.get(url);
        }
        return driver;
    }
}
