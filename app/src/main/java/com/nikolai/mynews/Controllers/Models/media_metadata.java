package com.nikolai.mynews.Controllers.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * a supplemental class for most popular articles - returns the meta-data about the articles
 */
public class media_metadata {
    @SerializedName("media-metadata")
    @Expose
    private List<URL> media_metadata;

    public media_metadata(List<URL> media_metadata) {
        this.media_metadata = media_metadata;
    }

    public List<URL> getMedia_metadata() {
        return media_metadata;
    }

    public void setMedia_metadata(List<URL> media_metadata) {
        this.media_metadata = media_metadata;
    }
}
