package com.nikolai.mynews.Controllers.Utils;

import com.nikolai.mynews.Controllers.Models.SearchArticles;
import com.nikolai.mynews.Controllers.Models.TopStories;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Cynthia Nikolai on 7/11/2019.
 */

public class ArticleSearchArticleStreams {

    private final static String API_KEY = "fd6A994KnuXHqfhl5WAHaTbnS3KxJe8J";

    public static Observable<SearchArticles> streamFetchNewsArticle() {
        ArticleSearchArticleService articleSearchArticleService = ArticleSearchArticleService.retrofit.create(ArticleSearchArticleService.class);
        return articleSearchArticleService.getAllNewsArticles("","","hamster","relevance", API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}

