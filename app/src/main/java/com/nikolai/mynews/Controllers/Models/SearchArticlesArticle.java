package com.nikolai.mynews.Controllers.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * the top level api call to the NY Times Search Articles API - returns information on the actual articles themselves
 */
public class SearchArticlesArticle {

    public String getSnippet() {
        return Snippet;
    }

    public void setSnippet(String snippet) {
        Snippet = snippet;
    }

    @SerializedName("snippet")
    @Expose
    private String Snippet;

    @SerializedName("web_url")
    @Expose
    private String web_url;

    @SerializedName("multimedia")
    @Expose
    private List<URL> multimedia;

    public SearchArticlesArticle(String web_url, List<URL> multimedia) {
        this.web_url = web_url;
        this.multimedia = multimedia;
    }

    public List<URL> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(List<URL> multimedia) {
        this.multimedia = multimedia;
    }

    public String getWeb_url() {
        return web_url;
    }

    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }

}
