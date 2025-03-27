package com.example.languageapp.feature_app.presentation.LanguageSelect

sealed class LanguageSelectEvent {

    data class SetLocale(val value: androidx.compose.ui.text.intl.Locale) : LanguageSelectEvent()
}