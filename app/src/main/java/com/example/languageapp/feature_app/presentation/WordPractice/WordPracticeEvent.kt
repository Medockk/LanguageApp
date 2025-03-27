package com.example.languageapp.feature_app.presentation.WordPractice

sealed class WordPracticeEvent {

    data object CheckClick: WordPracticeEvent()
    data class SetAnswer(val value: String) : WordPracticeEvent()
}