package com.luoxiaopan.movie.domain;

import java.util.List;

/**
 * 视频列表页面对象
 * @author luoxiaopan
 * @version 2017/3/25
 */
public class MovieListPage
{
    /**
     * 当前页面的视频页链接的集合
     */
    private List<String> movieUrlList;

    /**
     * 下一页的链接
     */
    private String nextPageUrl;

    public List<String> getMovieUrlList()
    {
        return movieUrlList;
    }

    public void setMovieUrlList(List<String> movieUrlList)
    {
        this.movieUrlList = movieUrlList;
    }

    public String getNextPageUrl()
    {
        return nextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl)
    {
        this.nextPageUrl = nextPageUrl;
    }

    @Override
    public String toString()
    {
        return "MovieListPage{\n" +
                "movieUrlList=" + movieUrlList +
                "\n, nextPageUrl='" + nextPageUrl + '\'' +
                '}';
    }
}
