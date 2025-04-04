package com.example.languageapp.feature_app.presentation.LanguageSelect

import androidx.annotation.StringRes

sealed class LanguageSelectEvent {

    data class SetLocale(val value: androidx.compose.ui.text.intl.Locale) : LanguageSelectEvent()
    data class SelectLanguage(@StringRes val stringRes: Int) : LanguageSelectEvent()
}