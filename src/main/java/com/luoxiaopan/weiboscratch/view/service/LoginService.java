package com.luoxiaopan.weiboscratch.view.service;

import com.luoxiaopan.weiboscratch.ScratchService;
import org.openqa.selenium.WebDriver;

/**
 * @author luoxiaopan
 * @version 2017/7/1
 */
public class LoginService
{
    public static void login(String userName, String password)
    {
        if (null != ScratchService.webDriver)
        {
            WebDriver webDriver = ScratchService.webDriver;
            String url = "http://www.weibo.com/";
            ScratchService.webDriver.get(url);
        }
    }
}
