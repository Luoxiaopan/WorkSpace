package com.luoxiaopan.text;

import com.luoxiaopan.util.CommonUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author luoxiaopan
 * @version 2017/6/17
 */
public class TextHandler
{
    public static void main(String[] args) throws IOException
    {
        List<String> questionList = CommonUtil.readFile("F:\\1.txt");
        List<String> answerLineList = CommonUtil.readFile("F:\\2.txt");
        List<String> answerList = new ArrayList<String>();
        for (String line : answerLineList)
        {
            answerList.addAll(Arrays.asList(line.replaceAll("\\d+[、．.]","").split(" ")));
        }
        int i = 0;
        for (String line : questionList)
        {
            if (line.matches("^\\d+.*"))
            {
                System.out.println("【" + answerList.get(i) + "】 " + line);
                i ++;
            }
            else
            {
                System.out.println(line);
            }

        }
    }
}
