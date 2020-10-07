package com.example.newsapp.ui.foryou;

import com.example.newsapp.ui.selectedcategory.SelectedCategory;

public class ForYouLoggedInItems extends SelectedCategory {

    private String sectionName;


    public ForYouLoggedInItems(String articleTitle, String articleContent, String articleImageUrl, String articleAuthor, String articleDate, String articleURL, String sectionName) {
        super(articleTitle, articleContent, articleImageUrl, articleAuthor, articleDate, articleURL);
        this.sectionName = sectionName;
    }

    public String getSectionName() {
        return sectionName;
    }

}
