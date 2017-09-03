package com.luoxiaopan.weiboscratch.view.button;

import com.luoxiaopan.weiboscratch.ScratchService;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author luoxiaopan
 * @version 2017/7/1
 */
public class StartChromeBtn
{
    private JButton button = new JButton("启动浏览器");

    public StartChromeBtn(final JTextField textField)
    {
        button.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                System.setProperty("webdriver.chrome.driver", "G:\\workspace\\webdriver\\chromedriver.exe");
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("start-maximized","test-type");
                ScratchService.webDriver = new ChromeDriver(chromeOptions);

            }
        });
    }

    public JButton getButton()
    {
        return button;
    }

    public void setButton(JButton button)
    {
        this.button = button;
    }

}
