package com.base;

import com.test.base.SeleniumBase;
import cucumber.api.Scenario;

import cucumber.runtime.model.CucumberScenario;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;


/**
 * Created by ashwad01 on 12/6/2016.
 */
public class WebDriverBase {
    private static final Logger LOG = LoggerFactory.getLogger(WebDriverBase.class);
    protected static WebDriver driver;

    private static HashMap<String, Object> generalData; // instead of using system.SetProperty.

    public WebDriverBase() {
        driver = SeleniumBase.getDriver();
        LOG.info(driver.toString());

    }
    public WebDriver getBaseUrl(){

        String url = SeleniumBase.getURL();
        driver.get(url);
        return driver;
    }

    public  WebDriver getDriver(){
        return driver;
    }


    public void completeScenario(Scenario scenario) {
        String s= scenario.getName() + ":" + scenario.getStatus();

        Collection<String> status = scenario.getSourceTagNames();
        LOG.info("++++++++++++++++++++Printing After Scenario++++++++++++++");
        LOG.info(s);
        for(String st:status){
            LOG.info(st);
        }

        LOG.info("*********************************************");
        LOG.info(scenario.getId());
        LOG.info("*********************************************");
    }
    public static void teardown(){

        SeleniumBase.driverCleanUp(driver);

    }
}
