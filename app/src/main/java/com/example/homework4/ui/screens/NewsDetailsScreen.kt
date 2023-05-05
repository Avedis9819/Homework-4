package com.example.homework4.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.homework4.files.News
import com.example.homework4.ui.theme.Homework4Theme

@Composable
fun NewsDetailsScreen(navController: NavController, news: News) {
    Homework4Theme {
        Scaffold(topBar = {
            TopAppBar(
                backgroundColor = Color.Transparent, elevation = 0.dp
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.padding(start = 10.dp)
                ) {
                    IconButton(
                        onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "arrow_back")
                    }
                }
            }
        }) {
            it
            Column(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = rememberAsyncImagePainter(model = news.urlToImage),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                )
                Text(text = news.title + "\n", style = MaterialTheme.typography.h5)

                news.author?.let {
                    Text(
                        text = news.content + "\n",
                        style = MaterialTheme.typography.body1
                    )
                }

                news.content?.let {
                    Text(
                        text = "Source: " + news.source.name,
                        style = MaterialTheme.typography.subtitle2
                    )
                }
            }
        }
    }
}