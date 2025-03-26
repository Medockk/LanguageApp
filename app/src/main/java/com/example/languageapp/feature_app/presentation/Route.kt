package com.example.languageapp.feature_app.presentation

sealed class Route(val route: String) {

    data object SplashScreen : Route("SplashScreen")
    data object OnBoardScreen : Route("OnBoardScreen")
    data object LoginScreen : Route("LoginScreen")
    data object SignUpScreen : Route("SignUpScreen")
}