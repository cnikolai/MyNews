package com.nikolai.mynews.Controllers.Utils;

import android.util.Log;

import com.nikolai.mynews.Controllers.Models.SearchArticles;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Cynthia Nikolai on 7/11/2019.
 */

public class ArticleSearchArticleStreams {
    private final static String TAG = "ArtSearchArtStreams";
    private final static String API_KEY = "fd6A994KnuXHqfhl5WAHaTbnS3KxJe8J";

    public static Observable<SearchArticles> streamFetchNewsArticle(Boolean Arts, Boolean Business, Boolean Entrepreneurs, Boolean Politics, Boolean Sports, Boolean Travel, Boolean Science, Boolean Technology, Boolean World, String begin_date, String end_date, String query_term) {
        String facet_fields = createFields(Arts, Business, Entrepreneurs, Politics, Sports, Travel, Science, Technology, World);
        Log.d(TAG, "streamFetchNewsArticle: end_date: "+end_date);
        Log.d(TAG, "streamFetchNewsArticle: start_date: "+begin_date);
        Log.d(TAG, "inside streamFetchNewsArticle");
        ArticleSearchArticleService articleSearchArticleService = ArticleSearchArticleService.retrofit.create(ArticleSearchArticleService.class);

        return articleSearchArticleService.getAllNewsArticles(facet_fields,begin_date,end_date,query_term, "newest",API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    private static String createFields(Boolean arts, Boolean business, Boolean entrepreneurs, Boolean politics, Boolean sports, Boolean travel, Boolean science, Boolean technology, Boolean world) {
        //at least one category will always be checked!!!
        String facet_string = "news_desk:(";
        if (arts) {
            facet_string += "\"Arts\" ";
        }
        if (business) {
            facet_string += "\"Business\" ";
        }
        if (entrepreneurs) {
            facet_string += "\"Entrepreneurs\" ";
        }
        if (politics) {
            facet_string += "\"Politics\" ";
        }
        if (sports) {
            facet_string += "\"Sports\" ";
        }
        if (travel) {
            facet_string += "\"Travel\" ";
        }
        if (science) {
            facet_string += "\"Science\" ";
        }
        if (technology) {
            facet_string += "\"Technology\" ";
        }
        if (world) {
            facet_string += "\"World\" ";
        }
        facet_string+=")";
        Log.d(TAG, "createFields: facet_string: " + facet_string);
        return facet_string;
    }
}

//https://api.nytimes.com/svc/search/v2/articlesearch.json?q=hamster&fq=news_desk:(%22Arts%22)&api-key=fd6A994KnuXHqfhl5WAHaTbnS3KxJe8J