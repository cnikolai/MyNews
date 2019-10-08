package com.nikolai.mynews.Controllers.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;
/**
 * the top level api call to the NY Times Top Stories API - returns information on the actual articles themselves
 */
public class TopStoriesArticle {
    @SerializedName("section")
    @Expose
    private String section;

    @SerializedName("subsection")
    @Expose
    private String subsection;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("byline")
    @Expose
    private String byline;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("published_date")
    @Expose
    private Date
            published_date;

    @SerializedName("multimedia")
    @Expose
    private List<URL> multimedia;

    private String id;

    public TopStoriesArticle(String section, String subsection, String title, String byline, String url, Date published_date, List<URL> multimedia) {
        this.section = section;
        this.subsection = subsection;
        this.title = title;
        this.byline = byline;
        this.url = url;
        this.published_date = published_date;
        this.multimedia = multimedia;
        this.id = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<URL> getMultimedia() {
        return multimedia;
    }

    public String getSection() {
        return section;
    }

    public String getSubsection() {
        return subsection;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getPublished_date() {
        return published_date;
    }
}
