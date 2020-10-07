package com.example.newsapp.ApiInterfaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SpecificArticle {
    @GET("svc/search/v2/articlesearch.json?api-key=gUCXczYe02MqNKHIhrGPUBv9t6sG5wTB")
    Call<com.example.newsapp.holders.specificarticle.SpecificArticle> getSpecificArticle(@Query("q") String searchedArticle);
}
