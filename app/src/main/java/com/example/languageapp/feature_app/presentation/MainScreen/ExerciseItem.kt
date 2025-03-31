package com.example.languageapp.feature_app.presentation.MainScreen

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class ExerciseItem(
    val background: Color,
    @DrawableRes val icon: Int,
    val title: String
)