package com.example.languageapp.feature_app.presentation.OnBoard

data class OnBoardState(
    val list: List<OnBoardItem> = onBoardList,

    val isComplete: Boolean = false,
    val exception: String = "",
    val currentPage: Int = 0,
)
