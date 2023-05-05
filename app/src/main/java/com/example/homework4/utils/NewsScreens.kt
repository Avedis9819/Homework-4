package com.example.homework4.utils

sealed class NewsScreens(val route: String) {
    object HomeScreen : NewsScreens("home_screen")
    object DetailScreen : NewsScreens("detail_screen")
}