package com.test;

import com.base.WebDriverBase;
import com.test.util.TestSupport;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.testng.Assert.assertEquals;


import com.map.LibraryUIMap;

/**
 * Created by ashwad01 on 11/18/2016.
 */
public class LibraryTest extends WebDriverBase {
    private static final Logger LOG = LoggerFactory.getLogger(LibraryTest.class);
    private WebDriver webDriver ;

    public LibraryTest() {
        super();
    }

    @Given("User opens library url")
    public void openURL() {
        LOG.info("Iam here");
        webDriver = getBaseUrl();

    }

    @Then("User goes to children page")
    public void navigateToChildrenPage() {
        TestSupport.waitForElementTobeClickable(webDriver, LibraryUIMap.CHILDREN_LOCATOR);
        TestSupport.click(webDriver, LibraryUIMap.CHILDREN_LOCATOR);
        String header = TestSupport.getDisplayedText(webDriver,LibraryUIMap.CHILDREN_PAGE_HEADER);
        assertEquals(header, "Children");
    }

    @Then("User navigates to children Books Movies and Music Page")
    public void navigateToBooksMoviesAndMusic() {
        TestSupport.waitForElementTobeClickable(webDriver, LibraryUIMap.BOOKS_MUSIC_LOCATOR);
        TestSupport.click(webDriver, LibraryUIMap.BOOKS_MUSIC_LOCATOR);
        String header =  TestSupport.getDisplayedText(webDriver,LibraryUIMap.SECOND_SECTION);
        assertEquals(header, "WHAT'S NEW / WHAT'S POPULAR Wrong" );
    }

    @After("@Web")
    public void completeScenario(Scenario scenario) {
        WebDriverBase.teardown();
        super.completeScenario(scenario);
    }
}
