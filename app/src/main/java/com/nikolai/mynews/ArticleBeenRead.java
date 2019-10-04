package com.nikolai.mynews;

import java.util.ArrayList;
import java.util.List;

/**
 * creates a list of articles that have already been read, so that it can change colors when the user clicks on it.  
 */
public class ArticleBeenRead {

    public static final String TAG = ArticleBeenRead.class.getSimpleName();

    //a list of the articles that have been read already
    private final List<String> mTopStoriesArticlesThatHaveBeenRead = new ArrayList<>();

    private static ArticleBeenRead instance;

    private ArticleBeenRead () {}

    public static ArticleBeenRead getInstance(){
        if(instance == null){
            instance = new ArticleBeenRead();
        }
        return instance;
    }

    public boolean hasBeenRead(String id) {
        return this.mTopStoriesArticlesThatHaveBeenRead.contains(id);
    }

    public void setArticleHasBeenRead(String id) {
        this.mTopStoriesArticlesThatHaveBeenRead.add(id);
    }
}

//https://www.journaldev.com/1377/java-singleton-design-pattern-best-practices-examples
//lazy initialization
