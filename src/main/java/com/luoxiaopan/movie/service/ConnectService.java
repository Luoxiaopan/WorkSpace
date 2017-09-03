package com.luoxiaopan.movie.service;

import com.luoxiaopan.movie.domain.MovieListPage;
import com.luoxiaopan.movie.domain.MoviePage;
import com.luoxiaopan.util.CommonUtil;
import com.luoxiaopan.util.Constants;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author luoxiaopan
 * @version 2017/3/25
 */
public class ConnectService
{

    OkHttpClient client = new OkHttpClient();

    /**
     * 访问网址，获取网页内容
     * @param url
     * @return
     */
    public String connect(String url)
    {
        System.out.println("Connect url : " + url);
        Request request = new Request.Builder()
                .url(url)
                .build();

        try
        {
            Response response = null;
            response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e)
        {
            System.err.println("Can't get the page of : " + url);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前页面的视频页面的链接
     * @param pageUrl 列表页的链接
     * @return 视频页面的链接
     */
    public MovieListPage getPageMovieUrls(String pageUrl)
    {
        MovieListPage listPage = new MovieListPage();


        List<String> urlList = new ArrayList<String>();
        listPage.setMovieUrlList(urlList);
        String pageMethod = connect(pageUrl);
        if (StringUtils.isNotEmpty(pageMethod))
        {
            //获取视频链接列表
            Document document = Jsoup.parse(pageMethod);
            Element element = document.getElementsByClass("movie_list").get(0).child(0);
            Elements hrefElements = element.getElementsByTag("a");
            for (Element hrefElement : hrefElements)
            {
                urlList.add(hrefElement.attr("href"));
            }

            //获取下一页链接
            String nextPageUrl = document.getElementsByClass("pagegbk").
                    get(0).attr("href");
            listPage.setNextPageUrl(nextPageUrl);

        }


        return listPage;
    }

    /**
     * 获取视频页面的下载链接
     * @param movieUrl
     * @return 视频页面的下载链接
     * @throws IOException
     */
    public MoviePage getDownloadPage(String movieUrl)
    {
        MoviePage moviePage = new MoviePage();
        String downloadUrl = null;
        String pageMethod = connect(movieUrl);
        if (StringUtils.isNotEmpty(pageMethod))
        {
            Document document = Jsoup.parse(pageMethod);
            Elements elements = document.getElementsByClass("downurl");
            if (elements.size() > 0)
            {
                downloadUrl = elements.get(0).child(0).attr("href");
                System.out.println("Get Download Url : " + downloadUrl);
                moviePage.setDownloadUrl(downloadUrl);
            }
            elements = document.getElementsByClass("film_title");
            if (elements.size() > 0)
            {
                String movieName = elements.get(0).child(0).html();
                moviePage.setMovieName(movieName);
            }
        }
        return moviePage;
    }

    public void downloadFile(MoviePage moviePage)
    {
        System.out.println("Download File From : " + moviePage.getDownloadUrl());
        String downloadUrl = moviePage.getDownloadUrl();
        Request request = new Request.Builder()
                .url(downloadUrl)
                .build();
        try
        {
            ResponseBody response = client.newCall(request).execute().body();
            String fileName = Constants.MOVIE_FILE_DIR + moviePage.getMovieName();
            CommonUtil.saveFile(response.byteStream(),fileName);
        }
        catch (IOException e)
        {
            System.err.println("Can't get the url of : " + downloadUrl);
            e.printStackTrace();
        }
    }
}
