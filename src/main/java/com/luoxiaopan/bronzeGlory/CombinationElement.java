package com.luoxiaopan.bronzeGlory;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author luoxiaopan
 * @version 2017/9/7
 */
public class CombinationElement
{
    private Stack<ConnectMapInfo> stack = null;

    private List<ConnectMapInfo> connectMapInfos;

    private List<List<ConnectMapInfo>> combineElements = null;

    public CombinationElement(List<ConnectMapInfo> connectMapInfos)
    {
        this.connectMapInfos = connectMapInfos;
    }

    public List<List<ConnectMapInfo>> combineElement()
    {
        stack = new Stack<>();
        combineElements = new ArrayList<>();
        recCombineElement();
        return combineElements;
    }

    private void recCombineElement()
    {
        if (stack.size() == connectMapInfos.size())
        {
            combineElements.add(new ArrayList<>(stack));
            return;
        }
        for (ConnectMapInfo element : connectMapInfos)
        {
            if (stack.contains(element))
            {
                continue;
            }
            stack.push(element);
            recCombineElement();
            stack.pop();
        }
    }
}
