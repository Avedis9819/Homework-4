package com.example.homework4

import android.content.ClipData.Item
import android.content.Intent

import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import android.widget.RadioGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.magnifier
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.List
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshots.SnapshotApplyResult
import androidx.compose.ui.Alignment
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.node.modifierElementOf
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.recyclerview.widget.AsyncListUtil
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.homework4.files.*
import com.example.homework4.Result
import com.example.homework4.ui.components.SearchBar
import com.example.homework4.ui.theme.Homework4Theme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import retrofit2.Response

class MainActivity : ComponentActivity() {
    val viewModel by viewModels<DataLoaderViewModel>()

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
                    var selectedFilter by remember { mutableStateOf("") }

                    Column(
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            SearchBar(onSearch = { query ->
                                viewModel.search(query = query)
                                Log.d(">>>", query)
                            })
                            Box() {
                                var expended by remember { mutableStateOf(false) }
                                IconButton(
                                    onClick = {
                                        expended = true
                                    }, modifier = Modifier
                                        .padding(2.dp)
                                        .height(40.dp)
                                ) {
                                    Icon(Icons.Outlined.List, contentDescription = "List")
                                }
                                val items = listOf(
                                    "Business",
                                    "Entertainment",
                                    "General",
                                    "Health",
                                    "Science",
                                    "Sports",
                                    "Technology"
                                )

                                DropdownMenu(
                                    expanded = expended,
                                    onDismissRequest = { expended = false }) {
                                    items.forEachIndexed { index, item ->
                                        DropdownMenuItem(onClick = {
                                            selectedFilter = item
                                            viewModel.loadNewsWithCategory(item.toString().lowercase())
                                            expended = false

                                        }) {
                                            Text(item)
                                        }
                                    }
                                }
                            }
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Get News: ")
                            IconButton(
                                onClick = {
                                    loading = true
                                    viewModel.getNews()
                                },
                            ) {
                                Icon(Icons.Filled.PlayArrow, contentDescription = null)
                            }
                            if (loading) {
                                CircularProgressIndicator()
                                LaunchedEffect(Unit) {
                                    delay(1000)
                                }
                            }
                        }
                        newsList(
                            newsResult = newsResult,
                            onRefresh = { viewModel.loadNewsWithCategory(selectedFilter) },
                            selectedFilter
                        )
                        loading = false
                    }
                }
            }
        }
    }

    @Composable
    fun newsList(
        newsResult: Result<Response<NewsResponse>>,
        onRefresh: () -> Unit,
        selectedCategory: String
    ) {
        var isRefreshing by remember { mutableStateOf(false) }

        when (newsResult) {
            is Result.Success -> {
                val currentNews = newsResult.data.body()?.articles.orEmpty()
                SwipeRefresh(
                    state = rememberSwipeRefreshState(isRefreshing),
                    onRefresh = {
                        isRefreshing = true
                        onRefresh()
                        CoroutineScope(Dispatchers.Default).launch {
                            delay(1000)
                            viewModel.loadNewsWithCategory(selectedCategory.toString().lowercase())
                            isRefreshing = false
                        }
                    }
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .padding(1.dp)
                    ) {
                        items(currentNews) { news ->
                            Card(
                                Modifier
                                    .padding(8.dp)
                                    .fillMaxWidth()
                                    .clickable(onClick = {

                                    })
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .fillMaxWidth()
                                ) {
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
    @Composable
    fun refreshWithCategory(category: String) {
        viewModel.loadNewsWithCategory(category.toString().lowercase())
    }


}

