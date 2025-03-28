package com.example.languageapp.feature_app.presentation.Animals

import com.example.languageapp.feature_app.domain.model.AnimalsModel

data class AnimalsState(
    val exception: String = "",
    val animal: AnimalsModel? = null,
    val userAnswer: String = "",
    val isRightAnswer: Boolean? = null,
)
