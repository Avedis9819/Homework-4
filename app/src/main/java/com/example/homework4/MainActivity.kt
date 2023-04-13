package com.example.homework4

import android.content.ClipData.Item
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.magnifier
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshots.SnapshotApplyResult
import androidx.compose.ui.Alignment
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.node.modifierElementOf
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.homework4.files.*
import com.example.homework4.Result
import com.example.homework4.ui.theme.Homework4Theme
import okhttp3.internal.wait
import retrofit2.Response

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<DataLoaderViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Homework4Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val newsResult = viewModel.news.observeAsState(Result.loading()).value
                    var loading by remember { mutableStateOf(false) }
                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(10.dp)
                                .fillMaxWidth()
                        ) {
                            TextField(
                                value = "",
                                onValueChange = {},
                                placeholder = { Text("Search here...") },
                                modifier = Modifier
                                    .width(200.dp)
                                    .height(50.dp)
                            )

                            Button(
                                onClick = { /*TODO*/ }, modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(10.dp)
                                    .height(40.dp)
                            ) {
                                Text(text = "\uD83D\uDD0D")
                            }

                            Button(
                                onClick = { /*TODO*/ }, modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(2.dp)
                                    .height(40.dp)
                            ) {
                                Text(text = "🍸")
                            }
                        }
                        Button(
                            onClick = {
                                loading = true
                                viewModel.getNews()
                            },
                            Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            Text(text = "Get News")
                        }
                        if (loading) {
                            Row() {
                                CircularProgressIndicator()
                            }
                        }
                        newsList(newsResult = newsResult)
                        loading = false
                    }
                }
            }
        }
    }
}


@Composable
fun newsList(newsResult: Result<Response<NewsResponse>>) {
    when(newsResult) {
        is Result.Success -> {
            val currentNews = newsResult.data.body()?.articles.orEmpty()
            LazyColumn(modifier = Modifier
                .padding(1.dp)) {

                items(currentNews) {news ->
                    Card(
                        Modifier
                            .padding(8.dp)
                            .fillMaxWidth()) {

                        Column(modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()) {
                            Image(
                                modifier = Modifier
                                    .height(200.dp)
                                    .fillMaxWidth(),
                                painter = rememberAsyncImagePainter(model = news.urlToImage),
                                contentDescription = null
                            )

                            Text(news.title, style = MaterialTheme.typography.h6)
                            Text(text = news.author.toString())
                        }
                    }
                }
            }
        }
        is Result.Error -> {
            println("Couldn't get in result")
            val newsError = newsResult.exception
            Text(text = newsError.toString())
        }
        else -> {

        }
    }
}

@Composable
fun loadingCircle() {
    CircularProgressIndicator(
        modifier = Modifier.size(30.dp)
    )
}
