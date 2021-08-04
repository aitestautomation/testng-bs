package com.browserstack;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.JavascriptExecutor;

public class SingleTest extends BrowserStackTestNGTest {

    @Test
    public void test() throws Exception {
        driver.get("https://www.google.com/ncr");
        try {
            WebElement ele = driver.findElement(By.className("IKl7Q"));
            if(ele.isDisplayed()) {
                ((JavascriptExecutor)driver).executeScript("arguments[0].scrollTo(0, arguments[0].scrollHeight)",ele);
                driver.findElement(By.xpath("//*[@id='L2AGLb']/div")).click();
            }
        }
        catch(Exception e){
            System.out.print(e);
        }
        WebElement element = driver.findElement(By.name("q"));
        element.sendKeys("BrowserStack");
        element.submit();
        Thread.sleep(5000);
        Assert.assertEquals("BrowserStack - Google Search", driver.getTitle());
    }
}
