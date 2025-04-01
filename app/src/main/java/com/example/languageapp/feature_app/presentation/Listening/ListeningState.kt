package com.example.languageapp.feature_app.presentation.Listening

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.languageapp.feature_app.domain.model.WordPracticeModel

data class ListeningState(
    val exception: String = "",
    val word: WordPracticeModel? = Test("cucumber", "[kkjkj]", "cucumber", listOf()),
    val isRightAnswer: Boolean? = null,
    val isListening: Boolean = false,

    val userAnswer: String = "",
    val microphoneSize: Dp = 100.dp
)

data class Test(
    override val word: String,
    override val transcription: String,
    override val rightAnswer: String,
    override val wrongAnswer: List<String>
) : WordPracticeModel