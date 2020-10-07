package com.example.newsapp.holders.specificarticle;

import com.google.gson.annotations.SerializedName;

public class Byline {
    public String getAuthorName() {
        return authorName;
    }

    @SerializedName("original")
    String authorName;

}
