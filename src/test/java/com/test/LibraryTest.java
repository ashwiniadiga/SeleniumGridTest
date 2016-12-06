package com.test;

import com.base.WebDriverBase;
import com.test.util.TestSupport;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.testng.Assert.assertEquals;


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
        String header = TestSupport.getDisplayedText(driver,LibraryUIMap.CHILDREN_PAGE_HEADER);
        assertEquals(header, "Children");
    }

    @Then("User navigates to children Books Movies and Music Page")
    public void navigateToBooksMoviesAndMusic() {
        TestSupport.waitForElementTobeClickable(driver, LibraryUIMap.BOOKS_MUSIC_LOCATOR);
        TestSupport.click(driver, LibraryUIMap.BOOKS_MUSIC_LOCATOR);
        String header =  TestSupport.getDisplayedText(driver,LibraryUIMap.SECOND_SECTION);
        assertEquals(header, "WHAT'S NEW / WHAT'S POPULAR Wrong" );
    }

    @After("@Web")
    public void completeScenario(Scenario scenario) {
        super.completeScenario(scenario);
    }
}
