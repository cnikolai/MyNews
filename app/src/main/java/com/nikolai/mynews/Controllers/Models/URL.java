package com.nikolai.mynews.Controllers.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
//    private String format;
//    private Integer height;
//    private Integer width;
//    private String type;
//    private String subtype;
//    private String caption;

}

//https://api.nytimes.com/svc/topstories/v2/business.json?api-key=fd6A994KnuXHqfhl5WAHaTbnS3KxJe8J
