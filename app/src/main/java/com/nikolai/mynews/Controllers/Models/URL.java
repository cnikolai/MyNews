package com.nikolai.mynews.Controllers.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * a supplemental class to top stories - returns the url of the article for viewing in the webview
 */
public class URL {
    public URL(String url) {
        this.url = url;
    }

    @SerializedName("url")
    @Expose
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

//https://api.nytimes.com/svc/topstories/v2/business.json?api-key=fd6A994KnuXHqfhl5WAHaTbnS3KxJe8J
