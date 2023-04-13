package com.example.homework4.files

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("status") val status: String?,
    @SerializedName("totalResults") val totalResults: Int,
    @SerializedName("articles") val articles: List<Article>)




