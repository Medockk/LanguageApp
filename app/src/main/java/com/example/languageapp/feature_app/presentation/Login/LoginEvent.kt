package com.example.languageapp.feature_app.presentation.Login

sealed class LoginEvent {

    data class EnterEmail(val value: String) : LoginEvent()
    data class EnterPassword(val value: String) : LoginEvent()
    data object ChangeIsShowPasswordState : LoginEvent()

    data object LoginClick: LoginEvent()
    data object ResetException: LoginEvent()
}