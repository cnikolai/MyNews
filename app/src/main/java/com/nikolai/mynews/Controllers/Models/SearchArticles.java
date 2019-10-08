package com.nikolai.mynews.Controllers.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * the top level API call - returns metadata on the articles returned
 */
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

    public SearchArticleResponse getResponse() {
        return response;
    }
}
