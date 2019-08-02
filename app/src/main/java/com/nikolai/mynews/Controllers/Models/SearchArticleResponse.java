package com.nikolai.mynews.Controllers.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchArticleResponse {

    @SerializedName("docs")
    @Expose
    private List<SearchArticlesArticle> docs;

    public List<SearchArticlesArticle> getDocs() {
        return docs;
    }

    public void setDocs(List<SearchArticlesArticle> docs) {
        this.docs = docs;
    }
}
