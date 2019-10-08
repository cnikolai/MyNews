package com.nikolai.mynews.Controllers.Utils;

import com.google.gson.GsonBuilder;
import com.nikolai.mynews.Controllers.Models.SearchArticles;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Cynthia Nikolai on 7/11/2019.
 */

/**
 * Uses retrofit, gson, and the API address and key to retrieve articles
 */
public interface ArticleSearchArticleService {

    String BASE_URL = "https://api.nytimes.com/";

    @GET("svc/search/v2/articlesearch.json")
    Observable<SearchArticles> getAllNewsArticles(@Query("facet_fields") String facet_fields, @Query("begin_date") String begin_date,@Query("end_date") String end_date,@Query("q") String q, @Query("sort") String sort_order, @Query("api-key") String apiKey);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssz").create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
}
