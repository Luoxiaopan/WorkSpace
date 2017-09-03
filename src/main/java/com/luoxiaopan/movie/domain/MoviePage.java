package com.luoxiaopan.movie.domain;

/**
 * 视频下载对象
 * @author luoxiaopan
 * @version 2017/3/25
 */
public class MoviePage
{
    /**
     * 视频下载链接
     */
    private String downloadUrl;

    private String movieName;

    public String getDownloadUrl()
    {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl)
    {
        this.downloadUrl = downloadUrl;
    }

    public String getMovieName()
    {
        return movieName;
    }

    public void setMovieName(String movieName)
    {
        this.movieName = movieName;
    }
}
