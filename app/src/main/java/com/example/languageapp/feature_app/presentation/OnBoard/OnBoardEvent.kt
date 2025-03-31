package com.example.languageapp.feature_app.presentation.OnBoard

sealed class OnBoardEvent {

    data class NextPage(val page: Int) : OnBoardEvent()
    data object ResetException: OnBoardEvent()
    data object SkipOnBoardClick: OnBoardEvent()

    data class SetQueueList(val list: List<OnBoardItem>) : OnBoardEvent()
}