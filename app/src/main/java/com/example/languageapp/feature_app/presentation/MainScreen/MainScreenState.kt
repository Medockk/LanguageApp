package com.example.languageapp.feature_app.presentation.MainScreen

import com.example.languageapp.feature_app.domain.model.TopUserModel

data class MainScreenState(
    val exception: String = "",
    val userImage: String = "https://uftclonibwagnofwkbtp.supabase.co/storage/v1/object/public/avatars//default_user_image.png",
    val userName: String = "",

    val topUserList: List<TopUserModel> = emptyList(),
    val showIndicator: Boolean = false,

)
