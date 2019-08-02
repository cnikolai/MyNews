package com.nikolai.mynews.Controllers.Utils;

import com.google.gson.GsonBuilder;
import com.nikolai.mynews.Controllers.Models.MostPopular;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Cynthia Nikolai on 7/11/2019.
 */

public interface MostPopularArticleService {

    String BASE_URL = "https://api.nytimes.com/svc/mostpopular/v2/viewed/";

    @GET("7.json")
    Observable<MostPopular> getAllNewsArticles(@Query("api-key") String apiKey);


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssz").create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
}

//https://api.nytimes.com/svc/mostpopular/v2/viewed/7.json?api-key=fd6A994KnuXHqfhl5WAHaTbnS3KxJe8J