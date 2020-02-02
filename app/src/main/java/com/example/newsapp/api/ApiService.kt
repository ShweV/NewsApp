package com.example.newsapp.api

import com.example.newsapp.model.News
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/v2/everything?q=bitcoin&sortBy=publishedAt")
    fun getResponse(@Query("from") date: String?, @Query("apiKey")apiKey:String?): Call<News>
}