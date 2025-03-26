package com.example.languageapp.feature_app.presentation.Login

import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _state = mutableStateOf(LoginState())
    val state: State<LoginState> = _state

    fun onEvent(event: LoginEvent) {
        when (event) {
            LoginEvent.ChangeIsShowPasswordState -> {
                _state.value = state.value.copy(
                    isShowPassword = !_state.value.isShowPassword
                )
            }

            is LoginEvent.EnterEmail -> {
                _state.value = state.value.copy(
                    email = event.value
                )
            }

            is LoginEvent.EnterPassword -> {
                _state.value = state.value.copy(
                    password = event.value
                )
            }

            LoginEvent.LoginClick -> {
                if (
                    Patterns.EMAIL_ADDRESS.matcher(_state.value.email).matches() &&
                    _state.value.password.length >= 6
                ){

                }else{
                    _state.value = state.value.copy(
                        exception = "some data wrong"
                    )
                }
            }

            LoginEvent.ResetException -> {
                _state.value = state.value.copy(
                    exception = ""
                )
            }
        }
    }
}