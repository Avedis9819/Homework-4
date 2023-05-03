package com.example.homework4

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework4.files.NewsResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class DataLoaderViewModel: ViewModel() {

    private val _news = MutableLiveData<Result<Response<NewsResponse>>>()
    val news: LiveData<Result<Response<NewsResponse>>> = _news

    fun getNews() {
        viewModelScope.launch {
            try {
                val response = DataSource().loadNews("")
                _news.postValue(Result.success(response))

            } catch (e: Exception) {
                println(e.message)
                _news.postValue(Result.Error(e))
            }
        }
    }


    fun loadNewsWithCategory(newsCategory: String) {
        viewModelScope.launch {
            try {
                val response = DataSource().loadNews(newsCategory.lowercase())
                _news.postValue(Result.success(response))
            } catch (e: Exception) {
                println(e.message.toString())
            }

        }
    }


    fun search(query: String) {
        viewModelScope.launch {
            try {
                val response = DataSource().searchNews(query)
                if(response.isSuccessful) {
                    val news = response.body()?.articles
                    if(news == null) {
                        _news.postValue(Result.loading("No Results Found"))
                    } else {
                        _news.postValue(Result.success(response))
                    }
                }
                Log.d(">>", response.message())
                _news.postValue(Result.success(response))

            } catch (e: Exception) {
                println(e.message.toString())
            }
        }
    }
}
//
sealed class Result<out T : Any> {
    data class Loading(val message: String = "") : Result<Nothing>()
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()

    companion object {
        fun <T : Any> loading(message: String = ""): Result<T> = Loading(message)
        fun <T : Any> success(data: T): Result<T> = Success(data)
        fun error(exception: Exception): Result<Nothing> = Error(exception)
        fun nothingFound(message: String): Result<Nothing> = Loading(message)
    }
}