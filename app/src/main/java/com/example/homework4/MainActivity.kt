package com.example.homework4

import android.content.ClipData.Item
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.magnifier
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshots.SnapshotApplyResult
import androidx.compose.ui.tooling.preview.Preview
import com.example.homework4.files.*
import com.example.homework4.Result
import com.example.homework4.ui.theme.Homework4Theme

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<DataLoaderViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Homework4Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val newsResult = viewModel.news.observeAsState(Result.loading()).value
                    Column {
                        Button(onClick = {viewModel.getNews()}) {
                            Text(text = "Get News")
                        }
                        newsList(newsResult = newsResult)
                    }
                }
            }
        }
    }
}


@Composable
fun newsList(newsResult: Result<NewsResponse>) {
    when(newsResult) {
        is Result.Success -> {
            val currentNews = newsResult.data
            LazyColumn(modifier = Modifier) {
                items(currentNews.articles) {Article ->
                    Card(
                        Modifier
                            .padding(8.dp)
                            .fillMaxWidth()) {

                        Column() {
                            Text(Article.title, style = MaterialTheme.typography.h2)
                            Text(Article.author, style = MaterialTheme.typography.h5)
                        }

                    }
                }
            }
        }
        is Result.Error -> {
            val newsError = newsResult.exception
            Text(text = newsError.toString())
        }
        else -> {

        }
    }
}