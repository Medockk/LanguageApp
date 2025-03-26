package com.example.languageapp.feature_app.presentation.SignUp

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _state = mutableStateOf(SignUpState())
    val state: State<SignUpState> = _state

    init {
        test()
    }

    fun test() {
        Thread{
            while (true){
                with(context) {
                    val manager = getSystemService(ConnectivityManager::class.java) as ConnectivityManager
                    val network = manager.allNetworks.any {
                        manager.getNetworkCapabilities(it)?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ?: false
                        manager.getNetworkCapabilities(it)?.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) ?: false
                    }
                    Log.e("callback", network.toString())
                }
            }
        }.start()
    }

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
            SignUpEvent.SignUpClick -> TODO()
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
}