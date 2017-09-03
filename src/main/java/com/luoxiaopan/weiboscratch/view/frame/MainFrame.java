package com.luoxiaopan.weiboscratch.view.frame;

import com.luoxiaopan.weiboscratch.view.button.LotteryBtn;
import com.luoxiaopan.weiboscratch.view.button.ScratchUserBtn;
import com.luoxiaopan.weiboscratch.view.button.StartChromeBtn;
import com.luoxiaopan.weiboscratch.view.text.UrlText;

import javax.swing.*;
import java.awt.*;

/**
 * @author luoxiaopan
 * @version 2017/7/1
 */
public class MainFrame
{
    public void startFrame()
    {
        JFrame frame = new JFrame("徐茵茵抽奖小程序");
        GridLayout layout = new GridLayout( 4, 1, 10, 10);
        frame.setLayout(layout);


        JTextField textField = new UrlText().getUrlTextField();
        frame.add(textField);
        frame.add(new StartChromeBtn(textField).getButton());
        frame.add(new ScratchUserBtn(textField).getButton());
        frame.add(new LotteryBtn().getButton());
        frame.setVisible(true);
        frame.setSize(300,200);
        frame.setAlwaysOnTop(true);
    }
}
