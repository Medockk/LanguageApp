package com.example.languageapp.feature_app.presentation.SignUp

import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.languageapp.feature_app.domain.use_case.Auth.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.exceptions.HttpRequestException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private val _state = mutableStateOf(SignUpState())
    val state: State<SignUpState> = _state

    fun onEvent(event: SignUpEvent){
        when (event){
            is SignUpEvent.ChangeCheckedState -> {
                _state.value = state.value.copy(
                    isChecked = event.value
                )
            }
            SignUpEvent.ChangePasswordState -> {
                _state.value = state.value.copy(
                    isPassword = !_state.value.isPassword
                )
            }
            SignUpEvent.ContinueClick -> {
                _state.value = state.value.copy(
                    isFirstRegisterPage = false
                )
            }
            SignUpEvent.BackClick -> {
                _state.value = state.value.copy(
                    isFirstRegisterPage = true
                )
            }
            is SignUpEvent.EnterConfirmPassword -> {
                _state.value = state.value.copy(
                    confirmPassword = event.value
                )
            }
            is SignUpEvent.EnterEmail -> {
                _state.value = state.value.copy(
                    email = event.value
                )
            }
            is SignUpEvent.EnterFirstName -> {
                _state.value = state.value.copy(
                    firstName = event.value
                )
            }
            is SignUpEvent.EnterLastName -> {
                _state.value = state.value.copy(
                    lastName = event.value
                )
            }
            is SignUpEvent.EnterPassword -> {
                _state.value = state.value.copy(
                    password = event.value
                )
            }
            SignUpEvent.SignUpClick -> {
                if (
                    Patterns.EMAIL_ADDRESS.matcher(_state.value.email).matches() &&
                    _state.value.password.isNotBlank() &&
                    _state.value.password.length >= 6 &&
                    _state.value.password == _state.value.confirmPassword &&
                    isStrongPassword(_state.value.password) &&
                    _state.value.firstName.isNotBlank() &&
                    _state.value.lastName.isNotBlank() &&
                    _state.value.isChecked
                ){
                    viewModelScope.launch(Dispatchers.IO) {
                        _state.value = state.value.copy(showIndicator = true)
                        try {
                            signUpUseCase(
                                _state.value.firstName,
                                _state.value.lastName,
                                _state.value.email,
                                _state.value.password
                            )
                            _state.value = state.value.copy(
                                isComplete = true
                            )
                        }catch (e: HttpRequestException){
                            _state.value = state.value.copy(
                                exception = e.localizedMessage ?: "xz"
                            )
                        }
                        catch (e: Exception) {
                            _state.value = state.value.copy(
                                exception = e.localizedMessage ?: "Unknown error"
                            )
                        }
                        _state.value = state.value.copy(
                            showIndicator = false
                        )
                    }
                }else if (
                    !Patterns.EMAIL_ADDRESS.matcher(_state.value.email).matches()
                ){
                    _state.value = state.value.copy(
                        exception = "wrong email"
                    )
                }else if (
                    _state.value.password != _state.value.confirmPassword
                ){
                    _state.value = state.value.copy(
                        exception = "password not equals"
                    )
                }else if (
                    !isStrongPassword(_state.value.password)
                ){
                    _state.value = state.value.copy(
                        exception = "password not strong"
                    )
                }else if (
                    !_state.value.isChecked
                ){
                    _state.value = state.value.copy(
                        exception = "agree with out rules"
                    )
                }else{
                    _state.value = state.value.copy(
                        exception = "some data wrong"
                    )
                }
            }
            SignUpEvent.ResetException -> {
                _state.value = state.value.copy(
                    exception = ""
                )
            }
            SignUpEvent.ChangeConfirmPasswordState -> {
                _state.value = state.value.copy(
                    isConfirmPassword = !_state.value.isConfirmPassword
                )
            }
        }
    }


    private fun isStrongPassword(password: String): Boolean {
        var isDigit = false
        var isUpperCase = false
        var isLowerCase = false

        password.forEach {
            if (it.isDigit()) isDigit = true
            if (it.isLowerCase()) isLowerCase = true
            if (it.isUpperCase()) isUpperCase = true
        }

        return isDigit && isLowerCase && isUpperCase
    }
}