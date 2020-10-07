package com.example.newsapp.holders.homearticles;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Media {
    @SerializedName("media-metadata")
    private List<ArticleMediaMetaData> mediaMetaDataList=null;

    public List<ArticleMediaMetaData> getMediaMetaDataList() {
        return mediaMetaDataList;
    }

    public void setMediaMetaDataList(List<ArticleMediaMetaData> mediaMetaDataList) {
        this.mediaMetaDataList = mediaMetaDataList;
    }

}
