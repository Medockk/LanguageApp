package com.example.languageapp.feature_app.presentation.Profile

sealed class ProfileEvent {

    data object ResetException: ProfileEvent()
    data object LogOut: ProfileEvent()
    data object ChangeIsLogOutState: ProfileEvent()
    data class ChangeSystemTheme(val value: Boolean): ProfileEvent()
}