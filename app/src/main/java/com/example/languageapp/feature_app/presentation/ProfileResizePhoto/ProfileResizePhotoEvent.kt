package com.example.languageapp.feature_app.presentation.ProfileResizePhoto

sealed class ProfileResizePhotoEvent {

    data object UseThatImageClick: ProfileResizePhotoEvent()
    data object ResetException: ProfileResizePhotoEvent()
}