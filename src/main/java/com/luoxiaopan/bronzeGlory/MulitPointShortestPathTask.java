package com.luoxiaopan.bronzeGlory;

import org.w3c.dom.ls.LSInput;

import java.util.*;
import java.util.concurrent.Callable;

/**
 * @author luoxiaopan
 * @version 2017/9/7
 */
public class MulitPointShortestPathTask
{

    public List<ConnectMapInfo> call()
    {
        ConnectMapInfo startNode = new ConnectMapInfo();
        List<List<ConnectMapInfo>> combineElementsList = new CombinationElement(null).combineElement();

        for (List<ConnectMapInfo> connectMapInfos : combineElementsList)
        {
            connectMapInfos.add(0,startNode);
            new SingleTask(connectMapInfos);
        }

        return new new ArrayList<ConnectMapInfo>();
    }

    // a--->b--->c--->d的最短路径
    private class SingleTask implements Callable<Object>
    {
        List<ConnectMapInfo> multiPointList;

        List<ConnectMapInfo> endPointList;

        public SingleTask(List<ConnectMapInfo> multiPointList, List<ConnectMapInfo> endPointList)
        {
            this.multiPointList = multiPointList;
            this.endPointList = endPointList;
        }

        public SingleTask(List<ConnectMapInfo> multiPointList)
        {
            this.multiPointList = multiPointList;
        }

        @Override
        public Object call() throws Exception
        {
            Set<ConnectMapInfo> posSet =  MapContext.getAllRegion().get(multiPointList.get(0).getRegionNo()).getPosSet();
            List<ConnectMapInfo> pointList = new ArrayList<>(posSet);
            pointList.removeAll(multiPointList);
            List<List<ConnectMapInfo>> multiStagePathList = new ArrayList<>();

            for (int i = 0; i < multiPointList.size() - 1; i++)
            {
                ConnectMapInfo startPoint = multiPointList.get(i);
                ConnectMapInfo endPoint = multiPointList.get(i + 1);
                pointList.add(startPoint);
                pointList.add(endPoint);
                List<ConnectMapInfo> path = shortestPathOf2Node(startPoint,endPoint,pointList);
                if (path == null)
                {
                    //当前路径无法继续获取了
                    break;
                    //return;
                }
                multiStagePathList.add(path);
                pointList.removeAll(path);
            }

            if (endPointList != null)
            {
                //防止终点出现在以前的路径中
                endPointList.removeAll(connectMulitStagePath(multiStagePathList));
                shortestPathOf2Nodes(multiPointList.get(multiStagePathList.size() - 1),endPointList,pointList);
            }

            //TODO 计算最后一个和终点的路径
            //
            //多阶段路径聚合在一起
            return connectMulitStagePath(multiStagePathList);
        }

        private List<ConnectMapInfo> shortestPathOf2Node(ConnectMapInfo startPoint, ConnectMapInfo endPoint, List<ConnectMapInfo> pointList)
        {
            //随意获取一个？
            int endPointIndex = pointList.indexOf(endPoint);
            int startPointIndex = pointList.indexOf(startPoint);
            List<Integer> path = MapContext.getPath(getDistanceGraph(startPointIndex,pointList), startPointIndex, endPointIndex);
            return indexToPoint(pointList,path);
        }

        private List<ConnectMapInfo> shortestPathOf2Nodes(ConnectMapInfo startPoint, List<ConnectMapInfo> endPoints, List<ConnectMapInfo> pointList)
        {
            //随意获取一个？
            int startPointIndex = pointList.indexOf(startPoint);
            int[][] distanceGraph = getDistanceGraph(startPointIndex,pointList);
            for (ConnectMapInfo endPoint : endPoints)
            {
                int endPointIndex = pointList.indexOf(endPoint);
                List<Integer> path = MapContext.getPath(distanceGraph, startPointIndex, endPointIndex);
                if (path != null)
                {
                    // 这里只返回了一个
                    return indexToPoint(pointList,path);
                }

            }
            return null;
        }

        private int[][] getDistanceGraph(int startPointIndex, List<ConnectMapInfo> pointList)
        {
            //每个区域间的距离表
            int[][] distanceGraph = new int[pointList.size()][pointList.size()];

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
                    if(pointList.get(i).getAroundPos().contains(pointList.get(j)))
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

            Set<Integer> notDoneSet = new HashSet<Integer>();
            for (int i = 0; i < pointList.size(); i++)
            {
                notDoneSet.add(i);
            }
            notDoneSet.remove(startPointIndex);
            while (!notDoneSet.isEmpty())
            {
                int closestPointIndex = -1;
                int shortestDistance = Integer.MAX_VALUE;
                for (Integer toCompareNo : notDoneSet)
                {
                    if(distanceGraph[startPointIndex][toCompareNo] < shortestDistance)
                    {
                        shortestDistance = distanceGraph[startPointIndex][toCompareNo];
                        closestPointIndex = toCompareNo;
                    }
                }

                //更新节点信息
                for (int i = 0; i < distanceGraph[startPointIndex].length; i++)
                {
                    if(distanceGraph[closestPointIndex][i] < Integer.MAX_VALUE )
                    {
                        if (distanceGraph[startPointIndex][i] > shortestDistance + distanceGraph[closestPointIndex][i])
                        {
                            distanceGraph[startPointIndex][i] = shortestDistance + distanceGraph[closestPointIndex][i];
                            distanceGraph[i][startPointIndex] = shortestDistance + distanceGraph[closestPointIndex][i];
                        }
                    }
                }
                //加入已经更新的节点中
                notDoneSet.remove(closestPointIndex);
            }
        }


        List<ConnectMapInfo> indexToPoint(List<ConnectMapInfo> pointList, List<Integer> path)
        {
            List<ConnectMapInfo> pathPointList = null;
            if (path != null)
            {
                pathPointList = new ArrayList<>();
                for (Integer index : path)
                {
                    pathPointList.add(pointList.get(index));
                }
            }
            return pathPointList;
        }

        List<ConnectMapInfo> connectMulitStagePath(List<List<ConnectMapInfo>> multiStagePathList)
        {
            List<ConnectMapInfo> pathList = new ArrayList<>();
            for (List<ConnectMapInfo> path : multiStagePathList)
            {
                // 注意下是不是可能有重复的点
                pathList.addAll(path);
            }
            return pathList;
        }
    }



}
