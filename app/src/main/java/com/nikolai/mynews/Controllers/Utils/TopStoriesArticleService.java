package com.nikolai.mynews.Controllers.Utils;

import com.google.gson.GsonBuilder;
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

public interface TopStoriesArticleService {

    public static final String BASE_URL = "https://api.nytimes.com/svc/topstories/v2/";

    @GET("{section}.json")
    Observable<TopStories> getAllNewsArticles(@Path("section") String section, @Query("api-key") String apiKey);


    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssz").create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
}
