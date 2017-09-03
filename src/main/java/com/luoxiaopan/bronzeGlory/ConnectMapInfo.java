package com.luoxiaopan.bronzeGlory;

import java.util.List;

/**
 * @author luoxiaopan
 * @version 2017/9/3
 */
public class ConnectMapInfo
{

    private String data;

    private Pos pos;

    private List<ConnectMapInfo> aroundPos;

    //用来标志当前点是属于哪个区域的 -1代表未设置区域
    private int regionNo = -1;

    public String getData()
    {
        return data;
    }

    public void setData(String data)
    {
        this.data = data;
    }

    public Pos getPos()
    {
        return pos;
    }

    public void setPos(Pos pos)
    {
        this.pos = pos;
    }

    public List<ConnectMapInfo> getAroundPos()
    {
        return aroundPos;
    }

    public void setAroundPos(List<ConnectMapInfo> aroundPos)
    {
        this.aroundPos = aroundPos;
    }

    public int getRegionNo()
    {
        return regionNo;
    }

    public void setRegionNo(int regionNo)
    {
        this.regionNo = regionNo;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConnectMapInfo that = (ConnectMapInfo) o;

        return pos.equals(that.pos);
    }

    @Override
    public int hashCode()
    {
        return pos.hashCode();
    }

    @Override
    public String toString()
    {
        return "ConnectMapInfo{" +
                "data='" + data + '\'' +
                ", pos=" + pos +
                ", regionNo=" + regionNo +
                '}';
    }
}
