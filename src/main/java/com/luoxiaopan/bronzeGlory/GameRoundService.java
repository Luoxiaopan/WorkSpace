package com.luoxiaopan.bronzeGlory;

import java.util.*;

/**
 * @author luoxiaopan
 * @version 2017/9/8
 */
public class GameRoundService
{

    public void gameRoundHandler()
    {
        RoundContext startRoundContext = new RoundContext();
        List<ConnectMapInfo> startNodeList = new ArrayList<>();
        List<ConnectMapInfo> passNodeList = new ArrayList<>();
        //如果可以直接砍死对面，则endNodeList = null;
        List<ConnectMapInfo> endNodeList = new ArrayList<>();

        List<ConnectMapInfo> pathList = new MulitPointShortestPathTask().call();

        //copy origin context
        RoundContext nextRound =  startRoundContext.nextContext(pathList);
        int[][] graph = nextRound.getDistanceGraph(nextRound.getMyPos().getRegionNo());

        int distance = graph[nextRound.getMyPos().getRegionNo()][nextRound.getEnemyPos().getRegionNo()];
        int canAttackNum = 0;
        if (distance > 2)
        {
            canAttackNum = 0;
        }
        if (distance <= 2)
        {
            Map<Integer,Integer> attackNumMap = new HashMap<>();
            for (ConnectMapInfo aroundNode : nextRound.getMyPos().getAroundPos()/* canAttackList */)
            {
                if (graph[nextRound.getMyPos().getRegionNo()][aroundNode.getRegionNo()] == 1)
                {
                    int attackNum = 1;
                    if (attackNumMap.containsKey(aroundNode.getRegionNo()))
                    {
                        attackNum = 1 + attackNumMap.get(aroundNode.getRegionNo());
                    }
                    attackNumMap.put(aroundNode.getRegionNo(),attackNum);
                }
            }
            canAttackNum = Collections.max(attackNumMap.values());
        }

        int myBlood = nextRound.getEnemyPlayer().getBlood();
        myBlood = myBlood - canAttackNum * 5 + nextRound.getEnemyPlayer().getEnergy();
        int enemyBlood = nextRound.getMyPlayer().getBlood();
        enemyBlood = enemyBlood - nextRound.getEnemyPlayer().getEnergy();


        //求出 maxScore !!!!!!!!!!!!!!!
        int score = myBlood - startRoundContext.getMyPlayer().getBlood()
                + startRoundContext.getEnemyPlayer().getBlood() - enemyBlood;
    }
}
