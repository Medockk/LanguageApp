package com.example.languageapp.feature_app.presentation.Profile

data class ProfileState(
    val exception: String = "",
    val userImage: String = "https://uftclonibwagnofwkbtp.supabase.co/storage/v1/object/public/avatars//default_user_image.png",
    val userName: String = "",
    val isLogOut: Boolean = false,
    val showIndicator: Boolean = false,
)
