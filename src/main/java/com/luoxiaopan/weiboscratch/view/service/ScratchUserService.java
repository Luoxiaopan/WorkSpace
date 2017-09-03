package com.luoxiaopan.weiboscratch.view.service;

import com.google.gson.Gson;
import com.luoxiaopan.util.CommonUtil;
import com.luoxiaopan.weiboscratch.ScratchService;
import com.luoxiaopan.weiboscratch.domain.WeiboUser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


/**
 * @author luoxiaopan
 * @version 2017/7/1
 */
public class ScratchUserService
{
    public static void scratchUser()
    {
        Set<WeiboUser> userSet = new LinkedHashSet<WeiboUser>();

        if (null != ScratchService.webDriver)
        {
            WebDriver webDriver = ScratchService.webDriver;
            String url = "http://www.weibo.com/";
            webDriver.get(url);

            try
            {
                Thread.sleep(1000);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            webDriver.findElement(By.id("loginname")).sendKeys("1271451020@qq.com");
            webDriver.findElement(By.name("password")).sendKeys("hrjlsgmwnk597");
            webDriver.findElement(By.className("W_btn_a")).click();

            visitRepostPage(webDriver, userSet, "https://weibo.cn/repost/F7ZRfwX8S?uid=3517132010&#rt");
            visitRepostPage(webDriver, userSet, "https://weibo.cn/repost/F8cNLpx0n?uid=3517132010&rl=1");

            BufferedWriter bufferedWriter = null;
            BufferedWriter bufferedWriter2 = null;
            try
            {
                bufferedWriter = new BufferedWriter(
                        new OutputStreamWriter(
                                new FileOutputStream("G:\\workspace\\Lottery\\user.txt")
                        ));
                bufferedWriter2 = new BufferedWriter(
                        new OutputStreamWriter(
                                new FileOutputStream("G:\\workspace\\Lottery\\index.html")
                        ));
                List<String> list = new ArrayList<String>();
                for (WeiboUser user : userSet)
                {
                    list.add(user.getUserName());
                    bufferedWriter.write(user.toString());
                    bufferedWriter.flush();
                }

                BufferedReader reader;
                reader = new BufferedReader(new FileReader("G:\\workspace\\Lottery\\index.origin.html"));
                String line;
                List<String> stringList = new ArrayList<String>();
                while ((line = reader.readLine()) != null)
                {
                    if (line.contains("var alldata"))
                    {
                        stringList.add("\t\tvar alldata = " + new Gson().toJson(list) +";");
                    }
                    else
                    {
                        stringList.add(line);
                    }
                }
                for (String lineStr : stringList)
                {
                    bufferedWriter2.write(lineStr);
                    bufferedWriter2.flush();
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }

            System.out.println(userSet);
        }
    }

    private static void visitRepostPage(WebDriver webDriver, Set<WeiboUser> userSet, String url)
    {
        webDriver.get(url);
        while (true)
        {
            userSet.addAll(getPostUser(webDriver));

            CommonUtil.waitTime(200);

            WebElement nextElement = webDriver.findElement(By.id("pagelist")).findElement(By.tagName("a"));
            if ("下页".equals(nextElement.getText()))
            {
                nextElement.click();
                ScratchService.waitId("pagelist");
            }
            else
            {
                break;
            }
        }
    }


    private static Set<WeiboUser> getPostUser(WebDriver webDriver)
    {
        Set<WeiboUser> userSet = new LinkedHashSet<WeiboUser>();
        List<WebElement> webElementList = webDriver.findElements(By.tagName("a"));
        for (WebElement userElement : webElementList)
        {
            String userId = userElement.getAttribute("href");
            if (userId.matches("https://weibo.cn/u/\\d+"))
            {
                String userName = userElement.getText();
                userSet.add(new WeiboUser(userName, userId, null));
            }
        }
        return userSet;
    }
}
