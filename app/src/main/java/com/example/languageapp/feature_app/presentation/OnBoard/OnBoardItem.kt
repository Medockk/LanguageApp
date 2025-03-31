package com.example.languageapp.feature_app.presentation.OnBoard

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.languageapp.R

data class OnBoardItem(
    @DrawableRes val image: Int,
    @StringRes val title: Int,
    @StringRes val description: Int,
    @StringRes val buttonText: Int,
)

val onBoardList = listOf(
    OnBoardItem(
        R.drawable.onboard_image_1,
        R.string.confidence_in_your_words,
        R.string.with_conversation_based_learning_you_ll_be_talking_from_lesson_one,
        R.string.Next
    ),
    OnBoardItem(
        R.drawable.onboard_image_2,
        R.string.take_your_time_to_learn,
        R.string.develop_a_habit_of_learning_and_make_it_a_part_of_your_daily_routine,
        R.string.More
    ),
    OnBoardItem(
        R.drawable.onboard_image_3,
        R.string.the_lessons_you_need_to_learn,
        R.string.using_a_variety_of_learning_styles_to_learn_and_retain,
        R.string.Choose_a_language
    ),
)
