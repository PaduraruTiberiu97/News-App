package com.example.newsapp.holders.specificarticle;

import com.google.gson.annotations.SerializedName;

public class Multimedia {
    @SerializedName("url")
    private String articleImageUrl;

    public String getArticleImageUrl() {
        return articleImageUrl;
    }

}
