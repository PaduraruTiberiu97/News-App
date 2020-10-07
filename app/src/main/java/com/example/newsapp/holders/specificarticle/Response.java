package com.example.newsapp.holders.specificarticle;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response {
    @SerializedName("docs")
    private List<Docs> docs;

    public List<Docs> getDocs() {
        return docs;
    }
}
