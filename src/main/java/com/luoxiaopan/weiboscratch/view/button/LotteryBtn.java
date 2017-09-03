package com.luoxiaopan.weiboscratch.view.button;

import com.luoxiaopan.weiboscratch.ScratchService;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author luoxiaopan
 * @version 2017/7/2
 */
public class LotteryBtn
{
    private JButton button = new JButton("开始抽奖");

    public LotteryBtn()
    {
        button.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (null != ScratchService.webDriver)
                {
                    ScratchService.webDriver.get("file:///D:/downloadSoftware/jiaoben763/Lottery/index.html");
                }

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
