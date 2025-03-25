package com.example.languageapp.feature_app.presentation

sealed class Route(val route: String) {

    data object SplashScreen : Route("SplashScreen")
    data object OnBoardScreen : Route("OnBoardScreen")
}