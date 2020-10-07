package com.example.newsapp.ApiInterfaces;

import com.example.newsapp.holders.homearticles.HomeArticleHolder;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MostPopularArticles {
    @GET("svc/mostpopular/v2/viewed/7.json?api-key=gUCXczYe02MqNKHIhrGPUBv9t6sG5wTB")
    Call<HomeArticleHolder> getMostPopularArticles();
}
