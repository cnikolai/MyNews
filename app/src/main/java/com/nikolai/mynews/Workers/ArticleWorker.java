package com.nikolai.mynews.Workers;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.nikolai.mynews.Controllers.Models.TopStories;
import com.nikolai.mynews.Controllers.Utils.TopStoriesArticleService;

import java.util.concurrent.TimeUnit;

public class ArticleWorker extends Worker {

    public static final String TAG = ArticleWorker.class.getSimpleName();
    private final static String API_KEY = "fd6A994KnuXHqfhl5WAHaTbnS3KxJe8J";

    private TopStories mTopStories;

    public ArticleWorker(
            @NonNull Context appContext,
            @NonNull WorkerParameters workerParams) {
            super(appContext, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Context applicationContext = getApplicationContext();

        try {
            //TODO: call api in blocking manner
            TopStoriesArticleService topStoriesArticleService = TopStoriesArticleService.retrofit.create(TopStoriesArticleService.class);
            mTopStories = topStoriesArticleService.getAllNewsArticles("business", API_KEY)
                    .timeout(10, TimeUnit.SECONDS)
                    .firstOrError().blockingGet();

            //show notification if new article (store which articles were fetched last time and compare them - use fetched articles singlteton).
            //every time app get killed, then shared prefs; store date at which fetch stuff and check dates to see if newer or not

        } catch (Throwable throwable) {
            throwable.printStackTrace();
            Log.e(TAG, "Error: ", throwable);
            return Result.failure();
        }
        return Result.success();
    }
}
