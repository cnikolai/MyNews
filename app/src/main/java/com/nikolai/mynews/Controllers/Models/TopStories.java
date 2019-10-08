package com.nikolai.mynews.Controllers.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * the top level API call - returns metadata on the articles returned
 */
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
  private Date last_updated;
    @SerializedName("num_results")
    @Expose
    private Integer num_results;
    @SerializedName("results")
    @Expose
    private List<TopStoriesArticle> results;

    public Date getLast_updated() {
        return last_updated;
    }

    public List<TopStoriesArticle> getResults() {
        return results;
    }
}
