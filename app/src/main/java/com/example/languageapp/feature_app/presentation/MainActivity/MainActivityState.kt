package com.example.languageapp.feature_app.presentation.MainActivity

data class MainActivityState(
    val isSystemInDarkTheme: Boolean = false,
    val systemLanguage: String = "en",
    val isLoadedFromDatabase: Boolean = false,

    val isUserDataNotEmpty: Boolean = false,
    val haveConnection: Boolean = true,
)
