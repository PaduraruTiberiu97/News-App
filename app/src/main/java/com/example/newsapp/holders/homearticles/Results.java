package com.example.newsapp.holders.homearticles;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Results {
    @SerializedName("url")
    private String articleUrl;
    @SerializedName("published_date")
    private String articlePublishedDate;
    @SerializedName("byline")
    private String articlePublisherName;
    @SerializedName("title")
    private String articleTitle;
    @SerializedName("abstract")
    private String articleContent;
    @SerializedName("media")
    private List<Media> media=null;

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    public String getArticlePublishedDate() {
        return articlePublishedDate;
    }

    public void setArticlePublishedDate(String articlePublishedDate) {
        this.articlePublishedDate = articlePublishedDate;
    }

    public String getArticlePublisherName() {
        return articlePublisherName;
    }

    public void setArticlePublisherName(String articlePublisherName) {
        this.articlePublisherName = articlePublisherName;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public List<Media> getMedia() {
        return media;
    }

    public void setMedia(List<Media> media) {
        this.media = media;
    }

}


