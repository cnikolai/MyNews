package com.nikolai.mynews.Controllers.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class SearchArticlesArticle {

    @SerializedName("abstract")
    @Expose
    private String Abstract;

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

    public String getAbstract() {
        return Abstract;
    }

    public void setAbstract(String anAbstract) {
        Abstract = anAbstract;
    }



}
