package com.test.util;

import com.google.common.collect.ComparisonChain;
import com.test.conf.SeleniumConfig;
import com.test.constants.TestConstants;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.testng.Assert.assertTrue;

public class TestSupport {
    private static final Logger LOG = LoggerFactory.getLogger(TestSupport.class);

    public static WebElement waitForElementShown (WebDriver driver, By locator ){
        WebDriverWait wait = createWebDriverWait(driver, TestConstants.NORMAL_WAIT);
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static WebElement waitForElementTobeClickable (WebDriver driver, By locator){
        WebDriverWait wait = createWebDriverWait(driver, TestConstants.NORMAL_WAIT);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static void click(WebDriver driver, By locator) {
        WebElement e = findElement(driver, locator);
        if (e != null) {
            e.click();
        } else
            LOG.error("Failed on clicking " + locator.toString());
    }

    /**
     * General click method on the ui element
     * @param element the UI element to be clicked
     */
    public static void click(WebElement element) {
        if (element != null) {
            LOG.debug("Clicking on" + element.toString());
            element.click();
        } else
            LOG.error("Failed on clicking " + element.toString());
    }

    /**
     * General typing method on the ui element
     * @param element   the UI element to receive the text data
     * @param text  the text going to type
     *
     * @param driver
     */
    public static void enterText(WebDriver driver,  By element, String text) {
        WebElement e = findElement(driver, element);
        if (e != null) {
            try {
                e.clear();
                e.sendKeys(text);
            }catch (InvalidElementStateException ex){
                LOG.warn(ex.toString());
            }
        } else
            LOG.error("Failed to enter data to " + element.toString());
    }

    /**
     * General Select visible text from dropdown box.
     * @param locator  the locator for the dropdown box where we have to select by visible text
     * @param visibleText  the text which we have to select from dropdown box
     * @param driver web driver
     */
    public static void selectByVisibleText(WebDriver driver,  By locator, String visibleText) {
        WebElement e = findElement(driver, locator);
        if (e != null) {
            LOG.info("Selecting " + visibleText + " in dropdown box " + locator.toString());
            new Select(e).selectByVisibleText(visibleText);

        } else
            LOG.error("Failed to enter data to " + locator.toString());
    }

    /**
     * General return displayed string for a locator
     * @param locator  the locator for the visible text
     * @param driver web driver
     */
    public static String getDisplayedText(WebDriver driver,  By locator) {
        String displayedText = "";
        WebElement e = findElement(driver, locator);
        if (e != null) {
            LOG.info("Retrieving text from the locator " + locator.toString());
            displayedText = e.getText().trim();
        } else {
            LOG.error("Failed to retrieve text from the locator " + locator.toString());
        }
        return displayedText;
    }

    /**
     * General return displayed string for an element
     * @param element  the web element for the visible text
     */
    public static String getDisplayedText(WebElement element) {
        String displayedText = "";
        if (element != null) {
            LOG.info("Retrieving text from the locator " + element.toString());
            displayedText = element.getText().trim();
        } else {
            LOG.error("Failed to retrieve text from the locator " + element.toString());
        }
        return displayedText;
    }

    /**
     * General return number of occurence of locator
     * @param locator  the locator for the element
     * @param driver web driver
     */
    public static int getNumberOfOccurenceOfLocator(WebDriver driver, By locator) {
        int count = 0;
        WebElement e = findElement(driver, locator);
        if (e != null) {
            LOG.info("Retrieving the number of occurrence of elements " + locator.toString());
            count = driver.findElements(locator).size();
        } else {
            LOG.error("Failed to retrieve the number of occurrence of elements" + locator.toString());
        }
        return count;
    }


    /***
     * Closes all open taps except the 0 index tab
     * TODO: Error handling and logging
     * @param driver WebDriver
     */
    public static void closeAllTabsExceptFirst(WebDriver driver)
    {
        List<String> browserTabs = new ArrayList<String>(driver.getWindowHandles());

        // loop until there is only one tab
        while (browserTabs.size() > 1)
        {
            // switch focus to LAST tab and close it
            driver.switchTo().window(browserTabs.get(browserTabs.size() - 1));
            driver.close();

            // switch to first tab to avoid exceptions
            driver.switchTo().window(browserTabs.get(0));

            // refresh the array of tabs
            browserTabs = new ArrayList<String> (driver.getWindowHandles());
        }
    }

    /**
     * Add RETRY logic based on selenium driver method [findElement]
     * @param element   the element to find
     * @param driver
     * @return  find the element, return it, otherwise returns null
     */
    public static WebElement findElement(WebDriver driver,  By element) {
        return findElement(driver, element, TestConstants.NORMAL_WAIT);
    }


    public static WebElement findElement(WebDriver driver,  By element, int waitTime) {
        WebElement webElement = null;
        WebDriverWait wait = createWebDriverWait(driver, waitTime);
        try {
            LOG.debug("Finding element={}", element.toString());
            webElement = wait.until(ExpectedConditions.visibilityOfElementLocated(element));
        } catch (Exception e) {
            LOG.debug(e.toString());
            LOG.error("Could not find element. Sleeping for 3s then retrying.");
            try {
                Thread.sleep(TestConstants.RETRY_SLEEP);
                webElement = wait.until(ExpectedConditions.visibilityOfElementLocated(element));
            } catch (Exception retryException) {
                LOG.error("Retry failed. This element could be missing or the locator may need to be updated. by={} e={}", element.toString(), retryException.toString());
                LOG.debug(null,e);
            }
        }
        finally {
            return webElement;
        }
    }


    public static void findSelectAndFindVisibleText(WebDriver driver,  By by, String selectOption, int waitTime) {
        WebElement element = findElement(driver, by, waitTime);
        if(element != null) {
            Select target = new Select(element);
            target.selectByVisibleText(selectOption);
        }
    }

    private static File createFolder()
    {
        String browserProfile = null;
        try {
            browserProfile = System.getProperty("profile");
        }
        catch (Exception exp){
            LOG.error("Selecting chrome driver since the driver value was not passed.");
        }
        if (browserProfile==null) {
            browserProfile = "local-chrome";
        }
        Calendar currentDate = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat(
                "yyyy/MMM/dd HH:mm:ss");
        String dateN = formatter.format(currentDate.getTime()).replace("/","_");
        String dateNow = dateN.replace(":","_");

        String path = SeleniumConfig.CONFIG.getString(browserProfile + ".webdriver" + ".platform") +dateNow;
        File directoryDate = new File(path);
        try {
            if (!directoryDate.isDirectory()) {
                directoryDate.mkdirs();
            }
        } catch (Exception e) {
            LOG.error("Error creating screenshot directory", e);
        }
        return directoryDate;
    }
    private static void takeSnapShot(WebDriver driver, String testMethodName) {

        File directory = createFolder();
        try {

            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            StringBuilder sb = new StringBuilder(directory.getAbsolutePath());
            sb.append("\\");
            sb.append(testMethodName);
            sb.append(".png");
            FileUtils.copyFile(screenshot, new File(sb.toString()));
            LOG.info("Screenshot taken: " + testMethodName);
        } catch (IOException e) {
            LOG.error("Error taking screenshot on error", e);
        }
    }
    private static WebDriverWait createWebDriverWait(WebDriver driver, int waitTime) {
        return new WebDriverWait(driver, TestConstants.NORMAL_WAIT);
    }

    private static Comparator<String> stringAlphabeticalComparator = new Comparator<String>() {
        public int compare(String str1, String str2) {
            return ComparisonChain.start().
                    compare(str1,str2, String.CASE_INSENSITIVE_ORDER).
                    compare(str1,str2).
                    result();
        }
    };

    private static Comparator<String> StringDateComparator = new Comparator<String>() {
        public int compare(String str1, String str2) {
            DateFormat format1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");

            try {
                Date dateField1 = format1.parse(str1);
                Date dateField2 = format1.parse(str2);
                return dateField1.compareTo(dateField2);

            }catch(Exception e){
                LOG.info(e.toString());

            }

        }
    };
    

}



