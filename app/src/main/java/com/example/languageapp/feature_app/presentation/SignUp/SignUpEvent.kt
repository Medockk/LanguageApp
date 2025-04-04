package com.example.languageapp.feature_app.presentation.SignUp

import android.app.Activity

sealed class SignUpEvent {

    data class EnterFirstName(val value: String) : SignUpEvent()
    data class EnterLastName(val value: String) : SignUpEvent()
    data class EnterEmail(val value: String) : SignUpEvent()
    data class EnterPassword(val value: String) : SignUpEvent()
    data class EnterConfirmPassword(val value: String) : SignUpEvent()
    data object ChangePasswordState: SignUpEvent()
    data object ChangeConfirmPasswordState: SignUpEvent()
    data class ChangeCheckedState(val value: Boolean) : SignUpEvent()

    data object ContinueClick: SignUpEvent()
    data object BackClick: SignUpEvent()
    data object SignUpClick: SignUpEvent()
    data object ResetException: SignUpEvent()
    data class DownloadPolitic(val activity: Activity?): SignUpEvent()
}