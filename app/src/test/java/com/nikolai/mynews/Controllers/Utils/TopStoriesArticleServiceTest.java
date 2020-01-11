package com.nikolai.mynews.Controllers.Utils;

import com.nikolai.mynews.Controllers.Models.TopStories;
import com.nikolai.mynews.Workers.ArticleProcessor;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class TopStoriesArticleServiceTest {
    private TopStories mTopStories;

    @Test
    public void test1() {
        TopStoriesArticleService topStoriesArticleService = TopStoriesArticleService.retrofit.create(TopStoriesArticleService.class);
        mTopStories = topStoriesArticleService.getAllNewsArticles("business", ArticleProcessor.API_KEY)
                .timeout(10, TimeUnit.SECONDS)
                .firstOrError().blockingGet();
    }

}