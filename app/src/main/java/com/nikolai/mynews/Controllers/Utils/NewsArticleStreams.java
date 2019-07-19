package com.nikolai.mynews.Controllers.Utils;

import com.nikolai.mynews.Controllers.Models.NewsArticle;
import com.nikolai.mynews.Controllers.Models.TopStories;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Cynthia Nikolai on 7/11/2019.
 */

public class NewsArticleStreams {

    private final static String API_KEY = "";

    public static Observable<TopStories> streamFetchNewsArticle() {
        NewsArticleService newsArticleService = NewsArticleService.retrofit.create(NewsArticleService.class);
        return newsArticleService.getAllNewsArticles("science", API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}

