package com.example.languageapp.feature_app.presentation.OnBoard

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.languageapp.R

data class OnBoardItem(
    @DrawableRes val image: Int,
    val title: String,
    val description: String,
    @StringRes val buttonText: Int,
)

val onBoardList = listOf(
    OnBoardItem(
        R.drawable.onboard_image_1,
        "Confidence in your words",
        "With conversation-based learning, you'll be talking from lesson one",
        R.string.Next
    ),
    OnBoardItem(
        R.drawable.onboard_image_2,
        "Take your time to learn",
        "Develop a habit of learning and make it a part of your daily routine",
        R.string.More
    ),
    OnBoardItem(
        R.drawable.onboard_image_3,
        "The lessons you need to learn",
        "Using a variety of learning styles to learn and retain",
        R.string.Choose_a_language
    ),
)
