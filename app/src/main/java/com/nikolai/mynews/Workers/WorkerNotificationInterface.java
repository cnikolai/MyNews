package com.nikolai.mynews.Workers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.nikolai.mynews.Controllers.Activities.SearchActivity;
import com.nikolai.mynews.R;

import static com.nikolai.mynews.Constants.CHANNEL_ID;
import static com.nikolai.mynews.Constants.NOTIFICATION_ID;

public interface WorkerNotificationInterface {

    public void showNotification();

}
