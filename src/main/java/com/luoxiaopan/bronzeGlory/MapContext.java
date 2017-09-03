package com.luoxiaopan.bronzeGlory;

import java.util.*;

/**
 * @author luoxiaopan
 * @version 2017/9/3
 */
public class MapContext
{

    private ConnectMapInfo[][] mapContext = null;

    //区域列表
    private Map<Integer,MapRegion> allRegion = new HashMap<Integer, MapRegion>();

    void initMapContext()
    {
        mapContext = new ConnectMapInfo[7][7];
    }

    void initMapArea()
    {
        ConnectMapInfo currentNode = null;
        int regionNo = -1;
        for (int i = 0; i < mapContext.length; i++)
        {
            for (int j = 0; j < mapContext[i].length; j++)
            {
                currentNode = mapContext[i][j];
                //==0 表示当前点还未进行区域初始化
                if(currentNode.getRegionNo() == -1)
                {
                    regionNo ++;
                    currentNode.setRegionNo(regionNo);
                    //生成一个新的区域
                    MapRegion currentRegion = new MapRegion();
                    currentRegion.setRegionNo(regionNo);
                    currentRegion.getPosSet().add(currentNode);
                    allRegion.put(regionNo,currentRegion);

                    //从这个点开始做深度遍历
                    dfs(currentNode);
                }
            }
        }
    }

    /**
     * 深度遍历 + 更新区域关联信息
     * @param startNode
     */
    void dfs(ConnectMapInfo startNode)
    {
        int regionNo = startNode.getRegionNo();
        MapRegion currentRegion = allRegion.get(regionNo);

        Queue<ConnectMapInfo> queue = new ArrayDeque<ConnectMapInfo>();
        queue.add(startNode);

        //被访问过的节点集合
        Set<ConnectMapInfo> visitedNodeSet = new HashSet<>();
        visitedNodeSet.add(startNode);

        while (!queue.isEmpty())
        {
            ConnectMapInfo currentNode = queue.poll();
            for (ConnectMapInfo aroundNode : currentNode.getAroundPos())
            {
                if (visitedNodeSet.contains(aroundNode))
                {
                    continue;
                }

                if (aroundNode.getData().equals(currentNode.getData()))
                {
                    aroundNode.setRegionNo(regionNo);
                    currentRegion.getPosSet().add(aroundNode);
                    queue.add(aroundNode);
                }
                //TODO 英雄不需要设置区域集合还是?
                /*else if(aroundNode.getData().equals("player"))
                {

                }*/
                //不属于当前点的集合，则需要更新周围点对应的区域，的关联区域集合
                else
                {
                    //需要该点已经初始化区域信息
                    if(aroundNode.getRegionNo() != -1)
                    {
                        //建立互相关联的关系
                        MapRegion aroundRegion = allRegion.get(aroundNode.getRegionNo());
                        aroundRegion.getConnectedRegionSet().add(regionNo);

                        currentRegion.getConnectedRegionSet().add(aroundRegion.getRegionNo());
                    }
                }

                visitedNodeSet.add(aroundNode);
            }
        }
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


    //TODO 计算区域之间的最短路径了
    public int[][] shortestRoute(Pos startPos,Pos endPos)
    {
        ConnectMapInfo startPosDetail= mapContext[startPos.getX()][startPos.getY()];
        ConnectMapInfo endPosDetail= mapContext[endPos.getX()][endPos.getY()];
        int startRegionNo = startPosDetail.getRegionNo();
        int endRegionNo = endPosDetail.getRegionNo();


        if (startRegionNo==-1 || endRegionNo == -1)
        {
            throw new RuntimeException("Maybe the area init error! " + startPos + " -------" + endPos);
        }
        //每个区域间的距离表
        int[][] distanceGraph = new int[allRegion.size()][allRegion.size()];

        for (int i = 0; i < distanceGraph.length; i++)
        {
            for (int j = 0; j < distanceGraph[i].length; j++)
            {
                if( i==j )
                {
                    distanceGraph[i][j] = 0;
                    continue;
                }
                //如果两个区域是连通的
                if(allRegion.get(i).getConnectedRegionSet().contains(j))
                {
                    distanceGraph[i][j] = 1;
                    distanceGraph[j][i] = 1;
                }
                else
                {
                    distanceGraph[i][j] = Integer.MAX_VALUE;
                    distanceGraph[j][i] = Integer.MAX_VALUE;
                }
            }
        }

        Set<Integer> doneSet = new HashSet<>();
        Set<Integer> notDoneSet = new HashSet<Integer>(allRegion.keySet());
        doneSet.add(startRegionNo);
        notDoneSet.remove(startRegionNo);
        while (!notDoneSet.isEmpty())
        {
            int closestRegionNo = -1;
            int shortestDistance = Integer.MAX_VALUE;
            for (Integer toCompareNo : notDoneSet)
            {
                if(distanceGraph[startRegionNo][toCompareNo] < shortestDistance)
                {
                    shortestDistance = distanceGraph[startRegionNo][toCompareNo];
                    closestRegionNo = toCompareNo;
                }
            }

            //更新节点信息
            for (int i = 0; i < distanceGraph[startRegionNo].length; i++)
            {
                if(distanceGraph[closestRegionNo][i] < Integer.MAX_VALUE )
                {
                    if (distanceGraph[startRegionNo][i] > shortestDistance + distanceGraph[closestRegionNo][i])
                    {
                        distanceGraph[startRegionNo][i] = shortestDistance + distanceGraph[closestRegionNo][i];
                        distanceGraph[i][startRegionNo] = shortestDistance + distanceGraph[closestRegionNo][i];
                    }
                }
            }
            //加入已经更新的节点中
            doneSet.add(closestRegionNo);
            notDoneSet.remove(closestRegionNo);
        }

        System.out.println("how to go :" + getPath(distanceGraph,startRegionNo,endRegionNo));

        return distanceGraph;
    }

    public List<Integer> getPath(int[][] distanceGraph,int startNo,int endNo)
    {
        Stack<Integer> path = new Stack<>();
        path.push(endNo);
        while (true)
        {
            Integer lastNo = path.peek();
            for (Integer nextNo : allRegion.get(lastNo).getConnectedRegionSet())
            {
                if(nextNo == startNo)
                {
                    path.push(nextNo);
                    Collections.reverse(path);
                    return path;
                }
                if (distanceGraph[startNo][nextNo] < distanceGraph[startNo][lastNo])
                {
                    path.push(nextNo);
                    continue;
                }
            }
        }
    }
}
