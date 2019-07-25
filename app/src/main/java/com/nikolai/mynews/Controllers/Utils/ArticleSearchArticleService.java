package com.nikolai.mynews.Controllers.Utils;

import com.google.gson.GsonBuilder;
import com.nikolai.mynews.Controllers.Models.SearchArticles;
import com.nikolai.mynews.Controllers.Models.TopStories;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Cynthia Nikolai on 7/11/2019.
 */

public interface ArticleSearchArticleService {

    //TODO: what if there are no results?
    public static final String BASE_URL = "https://api.nytimes.com/";

    //try with browser / retrofit
    //https://api.nytimes.com/svc/search/v2/articlesearch.json?q=hamster&fq=news_desk:("Arts" "Business")&api-key=fd6A994KnuXHqfhl5WAHaTbnS3KxJe8J

    @GET("svc/search/v2/articlesearch.json")
    Observable<SearchArticles> getAllNewsArticles(@Query("fq") String news_desk, @Query("begin_date") String begin_date, @Query("end_date") String end_date, @Query("q") String q, @Query("sort") String sort, @Query("api-key") String apiKey);


    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssz").create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
}
