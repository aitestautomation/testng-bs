package com.browserstack;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import org.testng.Assert;
import org.testng.annotations.Test;

public class SingleTest extends BrowserStackTestNGTest {

    @Test
    public void test() throws Exception {
    	  // navigate to bstackdemo
        driver.get("https://www.bstackdemo.com");
        Thread.sleep(5000);
    }
    
    @Test
    public void test2() throws Exception {
        // navigate to bstackdemo
        driver.get("https://www.youtube.com");
        Thread.sleep(5000);
    }
    
    @Test
    public void test3() throws Exception {
        // navigate to bstackdemo
        driver.get("https://www.google.com");
        Thread.sleep(5000);
    }
}
