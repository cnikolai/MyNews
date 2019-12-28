package com.nikolai.mynews;

import com.nikolai.mynews.Controllers.Models.TopStories;
import com.nikolai.mynews.Controllers.Models.TopStoriesArticle;
import com.nikolai.mynews.Controllers.Models.URL;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

public class TopStoriesTest {

    public static final String EXPECTED_LAST_UPDATED = "";
    public static final int EXPECTED_NUM_RESULTS = 33;
    //String section, String subsection, String title, String byline, String url, Date published_date, List<URL> multimedia

    private List<TopStoriesArticle> mTopStoriesArticles;
    private TopStories mTopStories;
    private TopStoriesArticle article1;
    private TopStoriesArticle article2;
    private TopStoriesArticle article3;

    //find a JSON string and parse it, include retrofit adapter

    @Before
    public void setUp() throws Exception {
        //article1 = new TopStoriesArticle(String section, String subsection, String title, String byline, String url, Date published_date, List< URL > multimedia);
        //article2 = new TopStoriesArticle();
        //article3 = new TopStoriesArticle();
       // mTopStoriesArticles = new List<TopStoriesArticle>();
        //mTopStoriesArticles.add(article1);
        //mTopStoriesArticles.add(article2);
        //mTopStoriesArticles.add(article3);
        //mTopStories = new TopStories();
    }

    @Test
    public void testUserDetails() throws Exception {
        Assert.assertEquals(EXPECTED_LAST_UPDATED, mTopStories.getLast_updated());
       // Assert.assertEquals(EXPECTED_, mTopStories.getResults());

    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Test Completed");

    }
}
