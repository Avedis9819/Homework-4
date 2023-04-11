package com.example.homework4

import com.example.homework4.data.NewsApiService
import com.example.homework4.data.RetroFitHelper
import com.example.homework4.files.NewsResponse

class DataSource {
    suspend fun loadNews(): NewsResponse {
        return RetroFitHelper.getInstance().create(NewsApiService::class.java).fetchNews("us")
    }
}