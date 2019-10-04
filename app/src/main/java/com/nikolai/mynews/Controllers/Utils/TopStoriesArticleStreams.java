package com.nikolai.mynews.Controllers.Utils;

import com.nikolai.mynews.Controllers.Models.TopStories;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Cynthia Nikolai on 7/11/2019.
 */

/**
 * Retrieves all top stories from the NY Times API
 */
public class TopStoriesArticleStreams {

    private final static String API_KEY = "fd6A994KnuXHqfhl5WAHaTbnS3KxJe8J";

    public static Observable<TopStories> streamFetchNewsArticle() {
        TopStoriesArticleService topStoriesArticleService = TopStoriesArticleService.retrofit.create(TopStoriesArticleService.class);
        return topStoriesArticleService.getAllNewsArticles("business", API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}

