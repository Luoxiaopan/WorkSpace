package com.luoxiaopan.weiboscratch.view.button;

import com.luoxiaopan.weiboscratch.ScratchService;
import com.luoxiaopan.weiboscratch.view.service.ScratchUserService;
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
public class ScratchUserBtn
{
    private JButton button = new JButton("获取抽奖用户");

    public ScratchUserBtn(final JTextField textField)
    {
        button.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                ScratchUserService.scratchUser();
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
