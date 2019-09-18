package com.nikolai.mynews.Workers;

import android.content.Context;
import android.util.Log;

import com.nikolai.mynews.Controllers.Models.TopStories;
import com.nikolai.mynews.Controllers.Utils.TopStoriesArticleService;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

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
            TopStoriesArticleService topStoriesArticleService = TopStoriesArticleService.retrofit.create(TopStoriesArticleService.class);
            mTopStories = topStoriesArticleService.getAllNewsArticles("business", API_KEY)
                    .timeout(10, TimeUnit.SECONDS)
                    .firstOrError().blockingGet();

            Date lastUpdated = mTopStories.getLast_updated();
            Date lastSeenUpdate = getLastSeenUpdate(lastUpdated); //using shared prefs

            if (lastUpdated > lastSeenUpdate) {
                showNotification();
            }
            saveLastUpdate(lastUpdated); //using shared prefs

            //show notification if new article (store which articles were fetched last time and compare them - use fetched articles singlteton).
            //every time app get killed, then shared prefs; store date at which fetch stuff and check dates to see if newer or not
            Log.e(TAG, "Success");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            Log.e(TAG, "Error: ", throwable);
            return Result.failure();
        }
        return Result.success();
    }
}
