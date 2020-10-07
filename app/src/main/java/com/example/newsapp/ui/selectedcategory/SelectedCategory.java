package com.example.newsapp.ui.selectedcategory;

import java.time.LocalDateTime;

public class SelectedCategory {
    String articleTitle;
    String articleContent;
    String articleImageUrl;
    String articleAuthor;
    String articleDate;
    String articleURL;

    public SelectedCategory(String articleTitle, String articleContent, String articleImageUrl, String articleAuthor, String articleDate,String articleURL) {
        this.articleTitle = articleTitle;
        this.articleContent = articleContent;
        this.articleImageUrl = "https://www.nytimes.com/" + articleImageUrl;
        this.articleAuthor = articleAuthor;
        this.articleDate = articleDate;
        this.articleURL=articleURL;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public String getArticleImageUrl() {
        return articleImageUrl;
    }

    public String getArticleAuthor() {
        return articleAuthor;
    }

    public String getArticleDate() {
       /* String stringCurrentTime=new SimpleDateFormat("HH", Locale.getDefault()).format(new Date());
        int currentTime=Integer.parseInt(stringCurrentTime);*/ //one way of getting the time

        LocalDateTime now = LocalDateTime.now();
        int hour = now.getHour();
        String substring = null;
        for (int i = 0; i < articleDate.length(); i++)
            if (articleDate.charAt(i) == 'T') {
                substring = articleDate.substring(i + 1);
            }
        for (int j = 0; j < substring.length(); j++)
            if (substring.charAt(j) == ':') {
                substring = substring.substring(0, j);
            }


        int timeAgo = Math.abs(hour - Integer.parseInt(substring)); //for positive value for int
        if (timeAgo == 0)
            return "Just now";
        else
            return timeAgo + "h ago";
    }

    public String getArticleURL() {
        return articleURL;
    }

}
