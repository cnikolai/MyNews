package com.nikolai.mynews.Controllers.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchArticles {
    @SerializedName("status")
    @Expose
  private String status;

    @SerializedName("copyright")
    @Expose
  private String copyright;

    @SerializedName("response")
    @Expose
    private SearchArticleResponse response;

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

    public SearchArticleResponse getResponse() {
        return response;
    }

    public void setResponse(SearchArticleResponse results) {
        this.response = results;
    }
}
