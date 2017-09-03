package com.luoxiaopan.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author luoxiaopan
 * @version 2017/3/25
 */
public class CommonUtil
{

    public static void saveFile(InputStream inputStream,String fileName)
    {
        System.out.println("Save File : " + fileName);
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try
        {
            bis = new BufferedInputStream(inputStream);
            byte[] buffer = new byte[2048];
            File file = new File(fileName);
            bos = new BufferedOutputStream(new FileOutputStream(file));
            int readLength = -1;
            while (true)
            {
                readLength = bis.read(buffer);
                if (readLength != -1)
                {
                    bos.write(buffer, 0, readLength);
                }
                else
                {
                    bos.flush();
                    break;
                }
            }
        }
        catch (IOException e)
        {
            System.err.println("Can't download the file of : " + fileName);
            e.printStackTrace();
        }
        finally
        {
            closeQuitely(inputStream);
            closeQuitely(bis);
            closeQuitely(bos);
        }
    }

    public static void closeQuitely(Closeable closeable)
    {
        if (null != closeable)
        {
            try
            {
                closeable.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
                System.err.println("Close io failed : " + e);
            }
        }
    }

    public static List<String> readFile(String filePath) throws IOException
    {
        BufferedReader reader;
        reader = new BufferedReader(new FileReader(filePath));
        String line;
        List<String> stringList = new ArrayList<String>();
        while ((line = reader.readLine()) != null)
        {
            stringList.add(line);
        }
        return stringList;
    }

    public static WebElement findElementByClasses(WebDriver webDriver, String matchClass, String classes)
    {
        List<WebElement> webElementList = webDriver.findElements(By.className(matchClass));

        if (webElementList == null)
        {
            return null;
        }

        Iterator<WebElement> iterator = webElementList.iterator();
        while (iterator.hasNext())
        {
            WebElement webElement = iterator.next();
            if (!classes.equals(webElement.getAttribute("class")))
            {
                iterator.remove();
            }
        }

        if (webElementList.isEmpty())
        {
            return null;
        }

        if (webElementList.size() > 1)
        {
            System.out.println("more than 1 webElement");
        }

        return webElementList.get(0);
    }

    public static void waitTime(long time)
    {
        try
        {
            Thread.sleep(time);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
