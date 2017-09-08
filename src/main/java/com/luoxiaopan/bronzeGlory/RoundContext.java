package com.luoxiaopan.bronzeGlory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author luoxiaopan
 * @version 2017/9/8
 */
public class RoundContext implements Cloneable
{

    private ConnectMapInfo[][] mapContext = null;

    //区域列表
    private Map<Integer,MapRegion> allRegion = new HashMap<Integer, MapRegion>();

    private ConnectMapInfo myPos;

    private ConnectMapInfo enemyPos;

    private PlayerInfo myPlayer;

    private PlayerInfo enemyPlayer;

    public PlayerInfo getMyPlayer()
    {
        return myPlayer;
    }

    public void setMyPlayer(PlayerInfo myPlayer)
    {
        this.myPlayer = myPlayer;
    }

    public PlayerInfo getEnemyPlayer()
    {
        return enemyPlayer;
    }

    public void setEnemyPlayer(PlayerInfo enemyPlayer)
    {
        this.enemyPlayer = enemyPlayer;
    }

    public ConnectMapInfo getMyPos()
    {
        return myPos;
    }

    public void setMyPos(ConnectMapInfo myPos)
    {
        this.myPos = myPos;
    }

    public ConnectMapInfo getEnemyPos()
    {
        return enemyPos;
    }

    public void setEnemyPos(ConnectMapInfo enemyPos)
    {
        this.enemyPos = enemyPos;
    }

    public ConnectMapInfo[][] getMapContext()
    {
        return mapContext;
    }

    public void setMapContext(ConnectMapInfo[][] mapContext)
    {
        this.mapContext = mapContext;
    }

    public Map<Integer, MapRegion> getAllRegion()
    {
        return allRegion;
    }

    public void setAllRegion(Map<Integer, MapRegion> allRegion)
    {
        this.allRegion = allRegion;
    }

    //获取走完路径后的context
    RoundContext nextContext(List<ConnectMapInfo> blankList)
    {
        RoundContext roundContext = new RoundContext();
        roundContext.mapContext = new ConnectMapInfo[mapContext.length][mapContext[0].length];
        for (int i = 0; i < mapContext.length; i++)
        {
            for (int j = 0; j < mapContext[i].length; j++)
            {
                mapContext[i][j] = new ConnectMapInfo();
            }
        }
        roundContext.mapContext = this.mapContext;

        roundContext.myPos = this.enemyPos;
        roundContext.enemyPos = mapContext[blankList.get(0).getPos().get()][blankList.get(0).getPos().getY()];
        //roundContext.initMap initRegion;
        for (ConnectMapInfo node : blankList)
        {
            if (node.getData().equals("blank"))
            {
                int energy = roundContext.enemyPlayer.getEnergy();
                energy = Integer.min(energy + 1,20);
            }
            if (canAttackEnemy)
            {
                roundContext.myPlayer.setBlood(roundContext.myPlayer.getBlood() - (roundContext.enemyPlayer.getEnergy() + 5));
                roundContext.enemyPlayer.setEnergy(0);
            }
            //update enemy blood;
        }

        return roundContext;
    }

    int[][] getDistanceGraph(int startRegion)
    {
        return new int[0][0];
    }
}
