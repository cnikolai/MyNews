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
        //TODO: put in categories too
        ArticleSearchArticleService articleSearchArticleService = ArticleSearchArticleService.retrofit.create(ArticleSearchArticleService.class);
        return articleSearchArticleService.getAllNewsArticles("news_desk:(\"Arts\")","20050101","","hamster","relevance", API_KEY)
        //return articleSearchArticleService.getAllNewsArticles("news_desk:(\"Arts\" \"Business\")","20190101","","hamster","relevance", API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}

//https://api.nytimes.com/svc/search/v2/articlesearch.json?q=hamster&fq=news_desk:(%22Arts%22)&api-key=fd6A994KnuXHqfhl5WAHaTbnS3KxJe8J