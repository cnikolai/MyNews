package com.nikolai.mynews.Controllers.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class SearchArticles {
    @SerializedName("status")
    @Expose
  private String status;
    @SerializedName("copyright")
    @Expose
  private String copyright;
    @SerializedName("meta")
    @Expose
    private Integer num_results;
    @SerializedName("results")
    @Expose
    private List<SearchArticlesArticle> results;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public Integer getNum_results() {
        return num_results;
    }

    public void setNum_results(Integer num_results) {
        this.num_results = num_results;
    }

    public List<SearchArticlesArticle> getResults() {
        return results;
    }

    public void setResults(List<SearchArticlesArticle> results) {
        this.results = results;
    }
}
