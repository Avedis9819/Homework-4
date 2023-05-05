package com.example.homework4.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.homework4.files.Filter
import com.example.homework4.files.News
import com.example.homework4.ui.components.FilterButton
import com.example.homework4.ui.components.NewsRefreshableList
import com.example.homework4.ui.components.SearchBar
import com.example.homework4.ui.theme.Homework4Theme


@Composable
fun NewsList(navController: NavHostController,
             news: List<News>,
             onRefresh: () -> Unit,
             onFilterSelected: (String) -> Unit,
             onSearch: (String) -> Unit)
{
    Homework4Theme {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Column(
            ) {
                Row(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SearchBar(onSearch = { query ->
                        onSearch(query)
                        Log.d(">>>", query)
                    })
                    Box() {
                        FilterButton(filterList = listOf(
                            Filter(1, "Business"),
                            Filter(2, "Entertainment"),
                            Filter(3, "General"),
                            Filter(4, "Health"),
                            Filter(5, "Science"),
                            Filter(6, "Sports"),
                            Filter(7, "Technology")
                        ),
                            onFilterSelected = { filter ->
                                onFilterSelected(filter.name)
                            })
                    }
                }
                NewsRefreshableList(navController = navController, news = news, onRefresh = onRefresh)
            }
        }
    }
}