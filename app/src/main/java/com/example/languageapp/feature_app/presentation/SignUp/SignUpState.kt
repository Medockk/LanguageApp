package com.example.languageapp.feature_app.presentation.SignUp

data class SignUpState(
    val exception: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val isPassword: Boolean = true,
    val isConfirmPassword: Boolean = true,
    val password: String = "",
    val confirmPassword: String = "",
    val isChecked: Boolean = false,

    val isFirstRegisterPage: Boolean = true,
    val isComplete: Boolean = false,
)
