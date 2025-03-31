package com.example.languageapp.feature_app.presentation

sealed class Route(val route: String) {

    data object SplashScreen : Route("SplashScreen")
    data object OnBoardScreen : Route("OnBoardScreen")
    data object LoginScreen : Route("LoginScreen")
    data object SignUpScreen : Route("SignUpScreen")
    data object NoConnectionScreen : Route("NoConnectionScreen")
    data object LanguageSelectScreen : Route("LanguageSelectScreen"){
        var isAfterSignUpScreen = true
    }
    data object MainScreen : Route("MainScreen")
    data object ProfileScreen : Route("ProfileScreen")
    data object ProfileResizePhotoScreen : Route("ProfileResizePhotoScreen"){
        var photo: ByteArray? = null
    }
    data object WordPractice: Route("WordPractice")
    data object Animals: Route("Animals")
    data object Listening: Route("Listening")
}