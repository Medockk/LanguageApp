package com.example.languageapp.feature_app.presentation.SignUp

import android.app.Activity

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
    val activity: Activity? = null,

    val isFirstRegisterPage: Boolean = true,
    val isComplete: Boolean = false,
    val showIndicator: Boolean = false,
)
