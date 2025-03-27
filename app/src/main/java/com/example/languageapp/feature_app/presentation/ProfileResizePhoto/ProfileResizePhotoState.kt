package com.example.languageapp.feature_app.presentation.ProfileResizePhoto

import android.graphics.Bitmap
import com.example.languageapp.feature_app.presentation.Route

data class ProfileResizePhotoState(
    val exception: String = "",

    val photoByteArray: ByteArray? = Route.ProfileResizePhotoScreen.photo,
    val photoBitmap: Bitmap? = null,
)
