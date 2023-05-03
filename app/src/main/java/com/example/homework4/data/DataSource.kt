package com.example.homework4

import android.util.Log
import com.example.homework4.data.NewsApiService
import com.example.homework4.data.RetroFitHelper
import com.example.homework4.files.NewsResponse
import retrofit2.Response

class DataSource {
    suspend fun loadNews(category: String): Response<NewsResponse> {
        return RetroFitHelper.getInstance().create(NewsApiService::class.java).fetchNews(category = category)
    }

    suspend fun searchNews(searchValue: String): Response<NewsResponse>{
        Log.d(">>>>", searchValue)
        return RetroFitHelper.getInstance().create(NewsApiService::class.java).fetchNews(title = searchValue)
    }
}