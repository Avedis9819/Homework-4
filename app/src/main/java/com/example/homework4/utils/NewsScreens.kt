package com.example.homework4.utils

sealed class NewsScreens(val route: String) {
    object HomeScreen: NewsScreens("home_screen")
    object DetailsScreen: NewsScreens("detail_screen")

}