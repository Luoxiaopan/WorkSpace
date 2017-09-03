package com.luoxiaopan.bronzeGlory;


import java.util.HashSet;
import java.util.Set;

/**
 * 地图上的区域信息
 * @author luoxiaopan
 * @version 2017/9/3
 */
public class MapRegion
{
    //区域编号
    private int regionNo;
    //当前区域中的点集合
    private Set<ConnectMapInfo> posSet = new HashSet<>();

    //和当前区域相连的区域集合
    private Set<Integer> connectedRegionSet = new HashSet<>();

    public int getRegionNo()
    {
        return regionNo;
    }

    public void setRegionNo(int regionNo)
    {
        this.regionNo = regionNo;
    }

    public Set<ConnectMapInfo> getPosSet()
    {
        return posSet;
    }

    public Set<Integer> getConnectedRegionSet()
    {
        return connectedRegionSet;
    }

    @Override
    public String toString()
    {
        return "MapRegion{" +
                "regionNo=" + regionNo +
                ", posSet=" + posSet +
                ", connectedRegionSet=" + connectedRegionSet +
                '}';
    }
}
