package com.example.newsapp.holders.homearticles;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeArticleHolder {
    @SerializedName("results")
    private List<Results> results=null;

    public List<Results> getResults() {
        return results;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }

}
