package com.browserstack;

import java.io.FileReader;
import java.net.URL;
import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.browserstack.local.Local;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

public class BrowserStackTestNGTest {
    
    private static final String URL = "hub-cloud.browserstack.com";
    public WebDriver driver;
    private Local l;

    @BeforeMethod(alwaysRun = true)
    @Parameters(value = {"deviceName", "osVersion", "browserName", "username", "key"})
    public void setup(String deviceName, String osVersion, String browserName, String username,
                                    String key) throws Exception {
    
        System.out.println("Device Name Hit "+deviceName);
        DesiredCapabilities capabilities = new DesiredCapabilities();
        HashMap<String, Object> bstackOptions = new HashMap<>();
        
        bstackOptions.put("deviceName", deviceName);
        bstackOptions.put("browserName", browserName);
        bstackOptions.put("osVersion", osVersion);
        bstackOptions.put("realMobile", "true");
        bstackOptions.put("appiumVersion", "1.22.0");
        bstackOptions.put("projectName", "Sample ");
        bstackOptions.put("buildName",
                          "Parallel Test "+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")));
        
        capabilities.setCapability("bstack:options", bstackOptions);
    
    
        driver = new RemoteWebDriver(
                new URL("http://" + username + ":" + key + "@" + URL + "/wd/hub"), capabilities);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() throws Exception {
        if (driver != null) {
            driver.quit();
        }
        if (l != null) {
            l.stop();
        }
    }
}
