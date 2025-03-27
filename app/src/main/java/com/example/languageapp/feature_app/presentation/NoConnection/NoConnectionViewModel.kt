package com.example.languageapp.feature_app.presentation.NoConnection

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NoConnectionViewModel @Inject constructor() : ViewModel() {

    private val _state = mutableStateOf(NoConnectionState())
    val state: State<NoConnectionState> = _state

    fun onEvent(event: NoConnectionEvent){
        when (event){
            NoConnectionEvent.CheckNetworkConnection -> {
                _state.value = state.value.copy(
                    isConnected = true
                )
            }
        }
    }
}