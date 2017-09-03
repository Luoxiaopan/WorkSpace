package com.luoxiaopan.bronzeGlory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author luoxiaopan
 * @version 2017/9/3
 */
public class TestMapArea
{
    public static void main(String[] args)
    {
        MapContext context = new MapContext();
        ConnectMapInfo[][] map = new ConnectMapInfo[7][7];
        Random random = new Random();
        for (int i = 0; i < map.length; i++)
        {
            for (int j = 0; j < map[i].length; j++)
            {
                ConnectMapInfo connectMapInfo = new ConnectMapInfo();
                Pos pos = new Pos();
                pos.setX(i);
                pos.setY(j);
                connectMapInfo.setPos(pos);
                connectMapInfo.setData("" + random.nextInt(5));
                map[i][j] = connectMapInfo;
            }
        }
        for (int i = 0; i < map.length; i++)
        {
            for (int j = 0; j < map[i].length; j++)
            {
                ConnectMapInfo connectMapInfo = map[i][j];
                connectMapInfo.setAroundPos(getAroundPos(map,i,j));
            }
        }

        printMap(map);
        System.out.println("***********************************");
        context.setMapContext(map);

        context.initMapArea();
        printMapRegion(map);
        System.out.println("***********************************");
        printAllRegion(context.getAllRegion());
        //System.out.println("***********************************");
        Pos a = new Pos(0,0);
        Pos b = new Pos(6,6);
        context.shortestRoute(a,b);
        //printDistance();
    }

    public static void printMap(ConnectMapInfo[][] map)
    {
        for (int i = 0; i < map.length; i++)
        {
            for (int j = 0; j < map[i].length; j++)
            {
                ConnectMapInfo connectMapInfo = map[i][j];
                System.out.print(connectMapInfo.getData() + "|");
            }
            System.out.println("\n-----------------");
        }
    }

    public static void printDistance(int[][] map)
    {
        for (int i = 0; i < map.length; i++)
        {
            for (int j = 0; j < map[i].length; j++)
            {
                System.out.print(map[i][j] + "|");
            }
            System.out.println("\n-----------------");
        }
    }

    public static void printMapRegion(ConnectMapInfo[][] map)
    {
        for (int i = 0; i < map.length; i++)
        {
            for (int j = 0; j < map[i].length; j++)
            {
                ConnectMapInfo connectMapInfo = map[i][j];
                System.out.print(connectMapInfo.getRegionNo() + "|");
            }
            System.out.println("\n-----------------");
        }
    }

    public static void printAllRegion(Map<Integer, MapRegion> allRegion)
    {
        for (MapRegion region : allRegion.values())
        {
            System.out.println(region);
        }
    }

    public static List<ConnectMapInfo> getAroundPos(ConnectMapInfo[][] map,int x,int y)
    {
        List<ConnectMapInfo> aroundPos = new ArrayList<>();
        for (int i = x-1; i <= x+1; i++)
        {
            if (i <0  || i >= map.length )
            {
                continue;
            }
            for (int j = y-1; j <= y+1; j++)
            {
                if ( j < 0 || j >= map[0].length )
                {
                    continue;
                }
                if (i == x && j == y)
                {
                    continue;
                }

                ConnectMapInfo connectMapInfo = map[i][j];
                aroundPos.add(connectMapInfo);
            }
        }
        return aroundPos;
    }

}
