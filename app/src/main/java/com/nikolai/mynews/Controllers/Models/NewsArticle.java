package com.nikolai.mynews.Controllers.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewsArticle {
    @SerializedName("section")
    @Expose
    private String section;

    @SerializedName("subsection")
    @Expose
    private String subsection;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("abstract")
    @Expose
    private String Abstract;

    @SerializedName("byline")
    @Expose
    private String byline;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("published_date")
    @Expose
    private String
            published_date;

    public NewsArticle(String section, String subsection, String title, String byline, String url, String published_date) {
        this.section = section;
        this.subsection = subsection;
        this.title = title;
        this.byline = byline;
        this.url = url;
        this.published_date = published_date;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSubsection() {
        return subsection;
    }

    public void setSubsection(String subsection) {
        this.subsection = subsection;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getByline() {
        return byline;
    }

    public void setByline(String byline) {
        this.byline = byline;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPublished_date() {
        return published_date;
    }

    public void setPublished_date(String published_date) {
        this.published_date = published_date;
    }

    public String getAbstract() {
        return Abstract;
    }

    public void setAbstract(String anAbstract) {
        Abstract = anAbstract;
    }



}

//https://api.nytimes.com/svc/topstories/v2/science.json?api-key=fd6A994KnuXHqfhl5WAHaTbnS3KxJe8J