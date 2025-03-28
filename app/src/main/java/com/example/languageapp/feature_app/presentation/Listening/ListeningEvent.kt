package com.example.languageapp.feature_app.presentation.Listening

sealed class ListeningEvent {

    data object CheckMySpeechClick: ListeningEvent()
    data object ChangeListeningClick: ListeningEvent()
    data object NextClick: ListeningEvent()
    data object ResetException: ListeningEvent()
    data class EnterUserAnswer(val value: String): ListeningEvent()
}