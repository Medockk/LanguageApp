package com.example.languageapp.feature_app.presentation.MainScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor() : ViewModel() {

    private val _state = mutableStateOf(MainScreenState())
    val state: State<MainScreenState> = _state

    init {

    }

    fun onEvent(event: MainScreenEvent){
        when (event){
            MainScreenEvent.ResetException -> {
                _state.value = state.value.copy(
                    exception = ""
                )
            }
        }
    }
}