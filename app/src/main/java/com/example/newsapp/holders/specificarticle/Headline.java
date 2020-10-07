package com.example.newsapp.holders.specificarticle;

import com.google.gson.annotations.SerializedName;

public class Headline {
    @SerializedName("main")
    private String articleTitle;

    public String getArticleTitle() {
        return articleTitle;
    }
}
