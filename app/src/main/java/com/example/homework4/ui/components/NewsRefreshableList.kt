package com.example.homework4.ui.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.homework4.files.News
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun NewsRefreshableList(
    navController: NavController,
    news: List<News>,
    onRefresh: () -> Unit
) {
    val state = rememberSwipeRefreshState(isRefreshing = false)

    SwipeRefresh(state = state , onRefresh = {
        onRefresh()
        state.isRefreshing = false
    }) {
        NewsList(navController = navController, news = news)
    }
}