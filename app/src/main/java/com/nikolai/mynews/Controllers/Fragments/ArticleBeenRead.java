package com.nikolai.mynews.Controllers.Fragments;

public class ArticleBeenRead {
    public static ArticleBeenRead getInstance() {
        return new ArticleBeenRead();
    }

    void setArticleHasBeenRead(String url) {

    }

    public boolean hasBeenRead(String url) {
        return false;
    }
}
