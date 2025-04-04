package com.example.languageapp.feature_app.presentation.MainScreen

sealed class MainScreenEvent {

    data object ResetException: MainScreenEvent()
}