package com.example.homework4

import com.example.homework4.data.NewsApiService
import com.example.homework4.data.RetroFitHelper
import com.example.homework4.files.NewsResponse
import retrofit2.Response

class DataSource {
    suspend fun loadNews(): Response<NewsResponse> {
        return RetroFitHelper.getInstance().create(NewsApiService::class.java).fetchNews()
    }
}