package com.example.homework4.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.homework4.files.News
import com.example.homework4.utils.NewsScreens
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun NewsList(
    navController: NavController,
    news: List<News>,
) {
    var isRefreshing by remember { mutableStateOf(false) }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = {
            isRefreshing = true
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(1.dp)
        ) {
            itemsIndexed(news) {index, news ->
                NewsCard(news = news) {
                    navController.navigate(NewsScreens.DetailsScreen.route + "/$index")
                }
            }
        }
    }
}

@Composable
fun NewsCard(news: News, onClick: () -> Unit) {
    Card(
        Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick)
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