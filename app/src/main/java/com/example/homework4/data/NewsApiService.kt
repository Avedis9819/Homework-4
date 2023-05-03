package com.example.homework4.data

import com.example.homework4.files.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("/v2/top-headlines?country=us&apiKey=571933f91c49464095e2c581a70ae264")
    suspend fun fetchNews(
        @Query("category") category: String = "",
        @Query("q") title: String = "") : Response<NewsResponse>
}