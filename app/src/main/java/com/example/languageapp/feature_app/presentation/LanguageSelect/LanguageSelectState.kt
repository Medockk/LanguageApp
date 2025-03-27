package com.example.languageapp.feature_app.presentation.LanguageSelect

data class LanguageSelectState(
    val motherLanguage: String = "English",

    val languageList: List<String> = listOf("English", "Russian", "Kazakh", "Chinese", "Japan", "Belarus"),
)
