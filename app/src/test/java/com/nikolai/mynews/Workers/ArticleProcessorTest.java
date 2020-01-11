package com.nikolai.mynews.Workers;

import android.content.SharedPreferences;

import androidx.work.ListenableWorker;

import com.nikolai.mynews.Controllers.Models.TopStories;
import com.nikolai.mynews.Controllers.Utils.TopStoriesArticleService;
import com.nikolai.mynews.SharedPreferencesWrapper;
import com.nikolai.mynews.SharedPreferencesWrapperInterface;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class ArticleProcessorTest {

    ArticleProcessor mArticleProcessor;

    @Mock
    TopStoriesArticleService mTopStoriesArticleService;
    @Mock
    SharedPreferencesWrapperInterface sharedPreferencesWrapper;
    @Mock
    WorkerNotificationInterface mWorkerNotificationInterface;

    @Before
    public void Before() {
        MockitoAnnotations.initMocks(this);
        mArticleProcessor = new ArticleProcessor(mTopStoriesArticleService,sharedPreferencesWrapper, mWorkerNotificationInterface);
    }

    @Test
    public void networkError() {
        //arrange
        when(mTopStoriesArticleService.getAllNewsArticles(any(),any())).thenReturn(Observable.error(IOException::new));
        //act
        ListenableWorker.Result result = mArticleProcessor.doWork();
        //assert
        assertEquals(ListenableWorker.Result.failure(),result);
        verify(mWorkerNotificationInterface,never()).showNotification();
        verifyZeroInteractions(sharedPreferencesWrapper);
    }

    @Test
    public void networkServerReturnsEmpty() {
        //arrange
        Date topStoriesDate = new Date(20000000);
        Date sharedPrefDate = new Date(40000000);
        when(mTopStoriesArticleService.getAllNewsArticles(any(),any())).thenReturn(Observable.just(new TopStories(topStoriesDate,new ArrayList<>())));
        when(sharedPreferencesWrapper.getLastSeenUpdate()).thenReturn(sharedPrefDate);
        //act
        ListenableWorker.Result result = mArticleProcessor.doWork();
        //assert
        assertEquals(ListenableWorker.Result.success(),result);
        verify(mWorkerNotificationInterface,never()).showNotification();
        verify(sharedPreferencesWrapper).saveLastUpdate(eq(topStoriesDate));
    }

    @Test
    public void networkServerReturnsList() {
        //arrange
        Date topStoriesDate = new Date(40000000);
        Date sharedPrefDate = new Date(20000000);
        when(mTopStoriesArticleService.getAllNewsArticles(any(),any())).thenReturn(Observable.just(new TopStories(topStoriesDate,new ArrayList<>())));
        when(sharedPreferencesWrapper.getLastSeenUpdate()).thenReturn(sharedPrefDate);
        //act
        ListenableWorker.Result result = mArticleProcessor.doWork();
        //assert
        assertEquals(ListenableWorker.Result.success(),result);
        verify(mWorkerNotificationInterface,never()).showNotification();
        verify(sharedPreferencesWrapper).saveLastUpdate(eq(topStoriesDate));
    }
}