package com.example.newsapp.ui.sections;

public class Sections {
    String sectionName;
     int sectionImage;

    public Sections(String sectionName,int sectionImage){
        this.sectionName=sectionName;
        this.sectionImage=sectionImage;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public int getSectionImage() {
        return sectionImage;
    }

    public void setSectionImage(int sectionImage) {
        this.sectionImage = sectionImage;
    }

}
