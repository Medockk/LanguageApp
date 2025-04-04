package com.example.languageapp.feature_app.presentation.MainActivity

sealed class MainActivityEvent {

    data class ChangeSystemTheme(val value: Boolean) : MainActivityEvent()
    data class ChangeSystemLanguage(val value: String) : MainActivityEvent()
    data object GetUserConfig: MainActivityEvent()
}