package com.example.homework4

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework4.files.NewsResponse
import kotlinx.coroutines.launch
import javax.sql.DataSource

class DataLoaderViewModel: ViewModel() {

    private val _news = MutableLiveData<Result<NewsResponse>>()
    val news: LiveData<Result<NewsResponse>> = _news

    fun getNews() {
        viewModelScope.launch {
            try {
                val response = DataSource().loadNews()
                _news.postValue(Result.success(response))
            } catch (e: Exception) {
                _news.postValue(Result.Error(e))
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
    }
}