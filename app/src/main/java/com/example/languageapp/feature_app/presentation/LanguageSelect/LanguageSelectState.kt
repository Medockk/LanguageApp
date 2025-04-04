package com.example.languageapp.feature_app.presentation.LanguageSelect

import androidx.annotation.StringRes
import com.example.languageapp.R

data class LanguageSelectState(
    @StringRes val motherLanguage: Int = R.string.english,

    @StringRes val languageList: List<Int> = listOf(
        R.string.english,
        R.string.russian,
        R.string.kazakh, R.string.chinese,
        R.string.japan,
        R.string.belarus
    ),
)
