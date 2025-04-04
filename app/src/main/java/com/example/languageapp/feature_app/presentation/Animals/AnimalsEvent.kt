package com.example.languageapp.feature_app.presentation.Animals

sealed class AnimalsEvent {

    data object ResetException: AnimalsEvent()
    data object CheckClick: AnimalsEvent()
    data object TryAgainClick: AnimalsEvent()

    data class EnterAnswer(val value: String) : AnimalsEvent()
}