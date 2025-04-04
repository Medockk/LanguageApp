package com.example.languageapp.feature_app.presentation.WordPractice

import com.example.languageapp.feature_app.domain.model.WordPracticeModel

data class WordPracticeState(
    val exception: String = "",

    val word: WordPracticeModel? = t("worddd", "вордд", "очко", listOf("очко", "глаза")),
    val userAnswer: String = "",

    val isUserCheckingAnswer: Boolean = false,
)

data class t(
    override val word: String,
    override val transcription: String,
    override val rightAnswer: String,
    override val wrongAnswer: List<String>
): WordPracticeModel
