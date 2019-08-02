package com.nikolai.mynews.Controllers.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class MostPopularArticle {
    @SerializedName("section")
    @Expose
    private String section;

//    @SerializedName("subsection")
//    @Expose
//    private String subsection;

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
    private Date
            published_date;

    @SerializedName("media")
    @Expose
    private List<media_metadata> media;

    public MostPopularArticle(String section, String title, String byline, String url, Date published_date, List<media_metadata> media) {
        this.section = section;
//        this.subsection = subsection;
        this.title = title;
        this.byline = byline;
        this.url = url;
        this.published_date = published_date;
        this.media = media;
    }

    public List<media_metadata> getMedia() {
        return media;
    }

    public void setMedia(List<media_metadata> media) {
        this.media = media;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

//    public String getSubsection() {
//        return subsection;
//    }
//
//    public void setSubsection(String subsection) {
//        this.subsection = subsection;
//    }

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

    public Date getPublished_date() {
        return published_date;
    }

    public void setPublished_date(Date published_date) {
        this.published_date = published_date;
    }

    public String getAbstract() {
        return Abstract;
    }

    public void setAbstract(String anAbstract) {
        Abstract = anAbstract;
    }



}
