package com.nikolai.mynews.Controllers.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * a supplemental class for the search articles model - returns a list of articles that were searched for
 */
public class SearchArticleResponse {

    @SerializedName("docs")
    @Expose
    private List<SearchArticlesArticle> docs;

    public List<SearchArticlesArticle> getDocs() {
        return docs;
    }
}
