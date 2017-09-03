package com.luoxiaopan.movie.service;

import com.luoxiaopan.movie.domain.MovieListPage;
import com.luoxiaopan.movie.domain.MoviePage;
import com.luoxiaopan.util.Constants;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author luoxiaopan
 * @version 2017/3/25
 */
public class StartUpService
{
    private static ConnectService connectService;

    public static void main(String[] args) throws IOException
    {
        BufferedWriter bufferedWriter = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream("G:\\movie\\download5.txt")
                ));

        connectService = new ConnectService();
        MovieListPage movieListPage = connectService.getPageMovieUrls(Constants.START_URL);
        List<String> movieUrlList = new ArrayList<String>(movieListPage.getMovieUrlList());
        MoviePage moviePage = null;
        while(true)
        {
            if (movieUrlList.isEmpty())
            {
                movieListPage = connectService.getPageMovieUrls(Constants.URL_PREFIX + movieListPage.getNextPageUrl());
                movieUrlList.addAll(movieListPage.getMovieUrlList());
            }
            for (String movieUrl : movieUrlList)
            {
                moviePage = connectService.getDownloadPage(Constants.URL_PREFIX + movieUrl);
                bufferedWriter.write(moviePage.getDownloadUrl() + "\r\n");
                bufferedWriter.flush();
            }
            movieUrlList.clear();
        }
    }
}
