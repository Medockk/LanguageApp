package com.example.languageapp.feature_app.presentation.ProfileResizePhoto

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileResizePhotoViewModel @Inject constructor() : ViewModel() {

    private val _state = mutableStateOf(ProfileResizePhotoState())
    val state: State<ProfileResizePhotoState> = _state

    init {
        _state.value = state.value.copy(
            photoBitmap = _state.value.photoByteArray?.toBitmap()
        )
    }

    fun onEvent(event: ProfileResizePhotoEvent){
        when (event){
            ProfileResizePhotoEvent.ResetException -> {
                _state.value = state.value.copy(exception = "")
            }
            ProfileResizePhotoEvent.UseThatImageClick -> {

            }
        }
    }

    private fun ByteArray.toBitmap() : Bitmap{
        return BitmapFactory.decodeByteArray(this, 0, this.size)
    }
}