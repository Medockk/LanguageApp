package com.example.languageapp.feature_app.presentation.Animals

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AnimalsViewModel @Inject constructor() : ViewModel() {

    private val _state = mutableStateOf(AnimalsState())
    val state: State<AnimalsState> = _state

    fun onEvent(event: AnimalsEvent){
        when (event){
            AnimalsEvent.CheckClick -> {
                _state.value = state.value.copy(
                    isRightAnswer = false
                )
            }
            is AnimalsEvent.EnterAnswer -> {
                _state.value = state.value.copy(
                    userAnswer = event.value
                )
            }
            AnimalsEvent.ResetException -> {
                _state.value = state.value.copy(
                    exception = ""
                )
            }

            AnimalsEvent.TryAgainClick -> {
                _state.value = state.value.copy(
                    isRightAnswer = true
                )
            }
        }
    }
}