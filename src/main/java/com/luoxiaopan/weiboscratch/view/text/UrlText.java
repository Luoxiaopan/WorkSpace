package com.luoxiaopan.weiboscratch.view.text;

import javax.swing.*;

/**
 * @author luoxiaopan
 * @version 2017/7/1
 */
public class UrlText
{
    private JTextField urlTextField;

    public UrlText()
    {
        urlTextField = new JTextField("http://www.weibo.com/");
    }

    public JTextField getUrlTextField()
    {
        return urlTextField;
    }

    public void setUrlTextField(JTextField urlTextField)
    {
        this.urlTextField = urlTextField;
    }

}
