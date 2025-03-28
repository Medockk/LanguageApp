package com.example.languageapp.feature_app.presentation.ProfileResizePhoto

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.languageapp.feature_app.domain.use_case.UserData.UpdateAvatarUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileResizePhotoViewModel @Inject constructor(
    private val updateAvatarUseCase: UpdateAvatarUseCase
) : ViewModel() {

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
                viewModelScope.launch(Dispatchers.IO) {
                    _state.value = state.value.copy(showIndicator = true)
                    try {
                        updateAvatarUseCase(_state.value.photoByteArray!!)
                        _state.value = state.value.copy(isComplete = true)
                    } catch (e: Exception) {
                        _state.value = state.value.copy(
                            exception = e.message.toString()
                        )
                    }
                    _state.value = state.value.copy(showIndicator = false)
                }
            }

            ProfileResizePhotoEvent.ChangeIsCompleteState -> {
                _state.value = state.value.copy(
                    isComplete = false
                )
            }
        }
    }

    private fun ByteArray.toBitmap() : Bitmap{
        return BitmapFactory.decodeByteArray(this, 0, this.size)
    }
}