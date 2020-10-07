package com.example.newsapp.holders.specificarticle;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Docs {
    @SerializedName("web_url")
    private String articleWebUrl;
    @SerializedName("snippet")
    private String articleContent;
    @SerializedName("multimedia")
    private List<Multimedia> multimedia;
    @SerializedName("headline")
    private Headline headline;
    @SerializedName("pub_date")
    private String publishedDate;
    @SerializedName("byline")
    private Byline byline;

    public Byline getByline() {
        return byline;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public Headline getHeadline() {
        return headline;
    }

    public String getArticleWebUrl() {
        return articleWebUrl;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public List<Multimedia> getMultimedia() {
        return multimedia;
    }


}
