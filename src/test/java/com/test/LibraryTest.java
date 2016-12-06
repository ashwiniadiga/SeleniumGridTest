package com.test;

import com.base.WebDriverBase;
import com.test.util.TestSupport;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.map.LibraryUIMap;

/**
 * Created by ashwad01 on 11/18/2016.
 */
public class LibraryTest extends WebDriverBase {
    private static final Logger LOG = LoggerFactory.getLogger(LibraryTest.class);


    @Given("User opens library url")
    public void openURL() {
        getDriver();

    }

    @Then("User goes to children page")
    public void navigateToChildrenPage() {
        TestSupport.waitForElementTobeClickable(driver, LibraryUIMap.CHILDREN_LOCATOR);
        TestSupport.click(driver, LibraryUIMap.CHILDREN_LOCATOR);
    }

    @After("@Web")
    public void completeScenario(Scenario scenario) {
        super.completeScenario(scenario);
    }
}
