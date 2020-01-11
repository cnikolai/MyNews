package com.nikolai.mynews.Workers;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.nikolai.mynews.Controllers.Models.TopStories;
import com.nikolai.mynews.Controllers.Utils.TopStoriesArticleService;
import com.nikolai.mynews.SharedPreferencesWrapper;
import com.nikolai.mynews.SharedPreferencesWrapperInterface;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * A worker class that provides new notifications to the user if a new article is released
 */
public class ArticleProcessor {

    public static final String TAG = ArticleProcessor.class.getSimpleName();
    public final static String API_KEY = "fd6A994KnuXHqfhl5WAHaTbnS3KxJe8J";
    private TopStoriesArticleService mTopStoriesArticleService;
    private SharedPreferencesWrapperInterface sharedPreferencesWrapper;
    private WorkerNotificationInterface mWorkerNotificationInterface;

    private TopStories mTopStories;


    public ArticleProcessor(TopStoriesArticleService topStoriesArticleService,SharedPreferencesWrapperInterface sharedPreferencesWrapperInterface, WorkerNotificationInterface mWorkerNotificationInterface) {
        this.mTopStoriesArticleService = topStoriesArticleService;
        this.sharedPreferencesWrapper = sharedPreferencesWrapperInterface;
        this.mWorkerNotificationInterface = mWorkerNotificationInterface;
    }

    @NonNull
    public ListenableWorker.Result doWork() {
        try {
            //TopStoriesArticleService topStoriesArticleService = TopStoriesArticleService.retrofit.create(TopStoriesArticleService.class);
            mTopStories = mTopStoriesArticleService.getAllNewsArticles("business", API_KEY)
                    .timeout(10, TimeUnit.SECONDS)
                    .firstOrError().blockingGet();
            Date lastUpdated = mTopStories.getLast_updated();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ", Locale.US);
            //Log.d(TAG, "doWork: lastUpdated" + lastUpdated.toString());
            String date = dateFormat.format(lastUpdated);
            //Log.d(TAG, "doWork: date - should be same" + date.toString());
            Date lastSeenUpdate = sharedPreferencesWrapper.getLastSeenUpdate(); //using shared prefs

            if (lastSeenUpdate.before(lastUpdated)) {
                mWorkerNotificationInterface.showNotification();
            }
            sharedPreferencesWrapper.saveLastUpdate(lastUpdated); //using shared prefs

            //Log.e(TAG, "Success");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            //Log.e(TAG, "Error: ", throwable);
            return ListenableWorker.Result.failure();
        }
        return ListenableWorker.Result.success();
    }

}
