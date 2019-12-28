package com.nikolai.mynews.Workers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.nikolai.mynews.Controllers.Activities.SearchActivity;
import com.nikolai.mynews.Controllers.Models.TopStories;
import com.nikolai.mynews.Controllers.Utils.TopStoriesArticleService;
import com.nikolai.mynews.R;
import com.nikolai.mynews.SharedPreferencesWrapper;
import com.nikolai.mynews.SharedPreferencesWrapperInterface;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import static com.nikolai.mynews.Constants.CHANNEL_ID;
import static com.nikolai.mynews.Constants.NOTIFICATION_ID;

/**
 * A worker class that provides new notifications to the user if a new article is released
 */
public class ArticleWorker extends Worker {

    public static final String TAG = ArticleWorker.class.getSimpleName();
    private final ArticleProcessor mArticleProcessor;

    public ArticleWorker(
            @NonNull Context appContext,
            @NonNull WorkerParameters workerParams) {
            super(appContext, workerParams);
            SharedPreferencesWrapper sharedPreferencesWrapper = new SharedPreferencesWrapper(appContext);
            WorkerNotification mWorkerNotificationInterface = new WorkerNotification(appContext);
        TopStoriesArticleService topStoriesArticleService = TopStoriesArticleService.retrofit.create(TopStoriesArticleService.class);
        mArticleProcessor = new ArticleProcessor(topStoriesArticleService,sharedPreferencesWrapper, mWorkerNotificationInterface);
    }

    @NonNull
    @Override
    public Result doWork() {
       return mArticleProcessor.doWork();
    }

}
