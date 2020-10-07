package com.example.newsapp.ui.foryou;

public class ForYouItems {
    int id;
    private int image;
    private String subSectionName;

    public ForYouItems(int id, int image, String subSectionName) {
        this.id = id;
        this.image = image;
        this.subSectionName = subSectionName;
    }

    public int getImage() {
        return image;
    }

    public String getSubSectionName() {
        return subSectionName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
