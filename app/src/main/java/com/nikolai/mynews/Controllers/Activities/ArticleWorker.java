package com.nikolai.mynews.Controllers.Activities;

import android.content.Context;

import com.google.common.util.concurrent.ListenableFuture;

import androidx.annotation.NonNull;
import androidx.work.ListenableWorker;
import androidx.work.WorkerParameters;

class ArticleWorker extends ListenableWorker {
    /**
     * @param appContext The application {@link Context}
     * @param workerParams Parameters to setup the internal state of this worker
     */
    public ArticleWorker(@NonNull Context appContext, @NonNull WorkerParameters workerParams) {
        super(appContext, workerParams);
    }

    @NonNull
    @Override
    public ListenableFuture<Result> startWork() {
        return null;
    }
}
