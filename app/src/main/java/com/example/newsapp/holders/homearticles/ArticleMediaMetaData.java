package com.example.newsapp.holders.homearticles;

import com.google.gson.annotations.SerializedName;

public class ArticleMediaMetaData {
    @SerializedName("url")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
