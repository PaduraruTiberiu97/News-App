package com.example.newsapp.holders.specificarticle;

import com.google.gson.annotations.SerializedName;

public class SpecificArticle {
    @SerializedName("response")
    private Response response;

    public Response getResponse() {
        return response;
    }
}
