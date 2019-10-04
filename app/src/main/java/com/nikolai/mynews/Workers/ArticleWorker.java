package com.nikolai.mynews.Workers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import com.nikolai.mynews.Controllers.Activities.SearchActivity;
import com.nikolai.mynews.Controllers.Models.TopStories;
import com.nikolai.mynews.Controllers.Utils.TopStoriesArticleService;
import com.nikolai.mynews.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

/**
 * A worker class that provides new notificaitons to the user if a new article is released
 */
public class ArticleWorker extends Worker {

    public static final String TAG = ArticleWorker.class.getSimpleName();
    private final static String API_KEY = "fd6A994KnuXHqfhl5WAHaTbnS3KxJe8J";
    private final String PREFERENCE_FILE_KEY = "MyNews";
    private static final String LAST_UPDATED = "lastUpdated";
    Context applicationContext;
    SharedPreferences sharedPref;

    private TopStories mTopStories;

    public ArticleWorker(
            @NonNull Context appContext,
            @NonNull WorkerParameters workerParams) {
            super(appContext, workerParams);

    }

    @NonNull
    @Override
    public Result doWork() {
        applicationContext = getApplicationContext();

        try {
            TopStoriesArticleService topStoriesArticleService = TopStoriesArticleService.retrofit.create(TopStoriesArticleService.class);
            mTopStories = topStoriesArticleService.getAllNewsArticles("business", API_KEY)
                    .timeout(10, TimeUnit.SECONDS)
                    .firstOrError().blockingGet();

            Date lastUpdated = mTopStories.getLast_updated();
            Date lastSeenUpdate = getLastSeenUpdate(lastUpdated); //using shared prefs

            if (lastSeenUpdate.before(lastUpdated)) {
                showNotification();
            }
            saveLastUpdate(lastUpdated); //using shared prefs

            Log.e(TAG, "Success");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            Log.e(TAG, "Error: ", throwable);
            return Result.failure();
        }
        return Result.success();
    }

    public void saveLastUpdate(Date lastUpdated) {
        sharedPref = applicationContext.getSharedPreferences(
                PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(LAST_UPDATED, lastUpdated.toString());
        editor.commit();
    }

    public Date getLastSeenUpdate(Date lastUpdated) {
        Instant instant;
        Date date;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        sharedPref = applicationContext.getSharedPreferences(
                PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        //LocalDateTime now = LocalDateTime.now();
        String now = sharedPref.getString(LAST_UPDATED, Instant.now().toString());
        //dateFormat.format(now);
        try {
            date = dateFormat.parse(now);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public void showNotification() {
        createNotificationChannel();
        addNotification();
    }

    private void addNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_menu_white_24dp)
                        .setContentTitle("My News Notification")
                        .setContentText("A New Article Has Been Released")
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent notificationIntent = new Intent(this, SearchActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
//
//         //Add as notification
//        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        manager.notify(0, builder.build());

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(notificationId, builder.build());

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "My News Notifications";
            String description = "My News Notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
