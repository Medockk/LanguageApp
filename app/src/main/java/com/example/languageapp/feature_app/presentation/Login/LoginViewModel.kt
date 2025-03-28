package com.example.languageapp.feature_app.presentation.Login

import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.languageapp.feature_app.domain.use_case.Auth.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.exceptions.HttpRequestException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : ViewModel() {

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
                    _state.value.password.isNotBlank() &&
                    _state.value.password.length >= 6
                ){
                    viewModelScope.launch(Dispatchers.IO) {
                        _state.value = state.value.copy(showIndicator = true)
                        try {
                            signInUseCase(
                                _state.value.email,
                                _state.value.password
                            )
                            _state.value = state.value.copy(
                                isSuccessfulLogin = true
                            )
                        } catch (e: HttpRequestException){
                            _state.value = state.value.copy(
                                exception = e.message.toString()
                            )
                        } catch (e: Exception) {
                            _state.value = state.value.copy(
                                exception = e.message.toString()
                            )
                        }
                        _state.value = state.value.copy(showIndicator = false)
                    }
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