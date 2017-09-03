package com.luoxiaopan.weiboscratch;

import com.google.common.collect.ImmutableList;
import com.luoxiaopan.weiboscratch.view.frame.MainFrame;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;

/**
 * @author luoxiaopan
 * @version 2017/7/1
 */
public class ScratchService
{
    public static WebDriver webDriver = null;

    public static void waitId(final String id)
    {
        WebDriverWait wait = new WebDriverWait(webDriver,10);
        wait.until(new ExpectedCondition<WebElement>(){
            public WebElement apply(WebDriver d) {
                return d.findElement(By.id(id));
            }}).click();
    }

    public static void main(String[] args) throws IOException
    {
        new MainFrame().startFrame();
    }
}
