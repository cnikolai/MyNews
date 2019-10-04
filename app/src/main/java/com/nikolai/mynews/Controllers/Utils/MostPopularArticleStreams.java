package com.nikolai.mynews.Controllers.Utils;

import com.nikolai.mynews.Controllers.Models.MostPopular;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Cynthia Nikolai on 7/11/2019.
 */

/**
 * Retrieves all most popular news stories from the NY Times API
 */
public class MostPopularArticleStreams {

    private final static String API_KEY = "fd6A994KnuXHqfhl5WAHaTbnS3KxJe8J";

    public static Observable<MostPopular> streamFetchNewsArticle() {
        MostPopularArticleService mostPopularArticleService = MostPopularArticleService.retrofit.create(MostPopularArticleService.class);
        return mostPopularArticleService.getAllNewsArticles(API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}

