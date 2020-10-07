package com.example.newsapp.ui.home;

public class HomeArticles {

    String articleTitle;
    String articleContent;
    String articleImageURL;
    String articleAuthor;
    String articleDate;
    String articleSiteUrl;

    public HomeArticles(String articleTitle, String articleContent, String articleSiteUrl, String articleImageURL, String articleAuthor, String articleDate) {
        this.articleTitle = articleTitle;
        this.articleContent = articleContent;
        this.articleSiteUrl = articleSiteUrl;
        this.articleImageURL = articleImageURL;
        this.articleAuthor = articleAuthor;
        this.articleDate = articleDate;

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

    public String getArticleImageURL() {
        return articleImageURL;
    }

    public void setArticleImageURL(String articleImageURL) {
        this.articleImageURL = articleImageURL;
    }

    public String getArticleAuthor() {
        return articleAuthor;
    }

    public void setArticleAuthor(String articleAuthor) {
        this.articleAuthor = articleAuthor;
    }

    public String getArticleDate() {
        return articleDate;
    }

    public void setArticleDate(String articleDate) {
        this.articleDate = articleDate;
    }

    public String getArticleSiteUrl() {
        return articleSiteUrl;
    }

    public void setArticleSiteUrl(String articleSiteUrl) {
        this.articleSiteUrl = articleSiteUrl;
    }

}
