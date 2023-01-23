package com.browserstack;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

public class SeleniumTest {
    public static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    @BeforeMethod(alwaysRun = true)
    @SuppressWarnings("unchecked")
    public void setUp() throws Exception {
        /* WebDriverManager will set up chromedriver to run tests on locally existing chrome */
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver.set(new ChromeDriver(options));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() throws Exception {
        driver.get().quit();
    }
}
