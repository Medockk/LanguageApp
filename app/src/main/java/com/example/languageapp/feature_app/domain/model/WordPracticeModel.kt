package com.example.languageapp.feature_app.domain.model

interface WordPracticeModel {

    val word: String
    val transcription: String
    val rightAnswer: String
    val wrongAnswer: List<String>
}