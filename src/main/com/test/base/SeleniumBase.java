package com.test.base;

import com.test.conf.SeleniumConfig;
import com.typesafe.config.Config;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;

import java.io.IOException;
import java.net.URL;

public class SeleniumBase {
    private static final Logger LOG = LoggerFactory.getLogger(SeleniumBase.class);
    private static Config CONFIG = SeleniumConfig.CONFIG;

    public static String getURL(){
        String url = CONFIG.getString("library.mainUrl");
        return url;
    }
    public static WebDriver getDriver() {
        WebDriver driver = null;

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

        if(browserProfile.equalsIgnoreCase("local-chrome")){
            driver = new ChromeDriver();
            LOG.info("Chrome driver created.");
        }
        else if(browserProfile.equalsIgnoreCase("local-firefox")){
            FirefoxProfile profile = new FirefoxProfile();
            profile.setPreference("plugin.state.fghgh", 2);
            driver = new FirefoxDriver(profile);
            LOG.info("Firefox driver created.");
        }
        else if(browserProfile.equalsIgnoreCase("local-iexplore")){
            driver = new InternetExplorerDriver();
        }
        else if(browserProfile.startsWith("remote")){
            // one of "remote-chrome" "remote-firefox" "remote-iexplore" (see webdriver.conf)
            try {
                Platform platform = Platform.valueOf(CONFIG.getString(browserProfile + ".webdriver" + ".platform"));
                if (browserProfile.toLowerCase().contains("remote-firefox"))    {
                    FirefoxProfile profile = new FirefoxProfile();
                    profile.setPreference("plugin.state.npasperaweb", 2);
                    DesiredCapabilities capabilities = DesiredCapabilities.firefox();
                    capabilities.setCapability(FirefoxDriver.PROFILE, profile);
                    driver = new RemoteWebDriver(new URL(CONFIG.getString("grid-main.url")), capabilities);
                }
                else {
                    DesiredCapabilities capabilities = new DesiredCapabilities(CONFIG.getString(browserProfile + ".webdriver" + ".browser"), "", platform);

                    //Get specified Grid URL if specified
                    String gridURL = System.getProperty("gridUrl");
                    if(gridURL == null) {
                        gridURL = CONFIG.getString("grid-main.url");
                    }

                    driver = new RemoteWebDriver(new URL(gridURL), capabilities);
                }

                //Allow access to local files used for testing even running remote drivers.
                RemoteWebDriver remote = (RemoteWebDriver) driver;
                remote.setFileDetector(new LocalFileDetector());
            } catch (IOException e) {
                LOG.error("Failed to setup driver: "+e.toString());
                driver = new ChromeDriver();
            }
        }
        if (driver==null) {
            throw new RuntimeException("Failed to find a suitable driver");
        }
        // maximize the browser window (if the browser window is too narrow, then the css/style will change)
        driver.manage().window().maximize();
        return driver;
    }
@AfterClass
    public static void driverCleanUp(WebDriver driver){
        try{
            if(System.getProperty("profile")!= null) {
                if (System.getProperty("profile").toLowerCase().contains("firefox")) {
                    Runtime.getRuntime().exec("taskkill /F /IM firefox.exe");
                    Thread.sleep(5000);
                    Runtime.getRuntime().exec("taskkill /F /IM plugin-container.exe");
                    Runtime.getRuntime().exec("taskkill /F /IM WerFault.exe");
                }
            }
            driver.quit();
            LOG.info("Driver cleanup done.");
        }
        catch(Exception exp){
            LOG.error("Exception while quiting Driver",exp);
        }
    }

}






