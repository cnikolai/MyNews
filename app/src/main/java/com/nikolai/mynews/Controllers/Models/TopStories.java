package com.nikolai.mynews.Controllers.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TopStories {
    @SerializedName("status")
    @Expose
  private String status;
    @SerializedName("copyright")
    @Expose
  private String copyright;
    @SerializedName("section")
    @Expose
  private String section;
    @SerializedName("last_updated")
    @Expose
  private String last_updated;
    @SerializedName("num_results")
    @Expose
    private Integer num_results;
    @SerializedName("results")
    @Expose
    private List<NewsArticle> results;

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

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(String last_updated) {
        this.last_updated = last_updated;
    }

    public Integer getNum_results() {
        return num_results;
    }

    public void setNum_results(Integer num_results) {
        this.num_results = num_results;
    }

    public List<NewsArticle> getResults() {
        return results;
    }

    public void setResults(List<NewsArticle> results) {
        this.results = results;
    }
}
