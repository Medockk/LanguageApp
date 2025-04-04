package com.example.languageapp.feature_app.presentation.Login

data class LoginState(
    val exception: String = "",
    val email: String = "",
    val password: String = "",
    val isShowPassword: Boolean = true,
    val showIndicator: Boolean = false,

    val isSuccessfulLogin: Boolean = false,
)
