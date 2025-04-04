package com.example.languageapp.feature_app.presentation.Profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.languageapp.feature_app.domain.use_case.Auth.SignOutUseCase
import com.example.languageapp.feature_app.domain.use_case.UserData.ClearUserDataAndConfigUseCase
import com.example.languageapp.feature_app.domain.use_case.UserData.GetUserDataUseCase
import com.example.languageapp.feature_app.domain.use_case.UserData.UpsertUserConfigUseCase
import com.example.languageapp.feature_app.domain.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.exceptions.HttpRequestException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase,
    private val getUserDataUseCase: GetUserDataUseCase,
    private val upsertUserConfigUseCase: UpsertUserConfigUseCase,
    private val clearUserDataAndConfigUseCase: ClearUserDataAndConfigUseCase
) : ViewModel() {

    private val _state = mutableStateOf(ProfileState())
    val state: State<ProfileState> = _state

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getUserData()
        }
    }

    private suspend fun getUserData() {
        getUserDataUseCase().collect {
            when (it) {
                is NetworkResult.Error<*> -> {
                    _state.value = state.value.copy(
                        showIndicator = false,
                        exception = it.message ?: "Unknown error"
                    )
                }

                is NetworkResult.Loading<*> -> {
                    _state.value = state.value.copy(
                        showIndicator = true
                    )
                }

                is NetworkResult.Success<*> -> {
                    withContext(Dispatchers.Main) {
                        _state.value = state.value.copy(
                            showIndicator = false,
                            userImage = it.data?.avatar ?: "",
                            userName = (it.data?.firstName ?: "") + " " + (it.data?.lastName ?: "")
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            ProfileEvent.LogOut -> {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        signOutUseCase()
                        clearUserDataAndConfigUseCase()
                        _state.value = state.value.copy(
                            isLogOut = true
                        )
                    } catch (e: HttpRequestException) {
                        _state.value = state.value.copy(
                            exception = e.message.toString()
                        )
                    } catch (e: Exception) {
                        _state.value = state.value.copy(
                            exception = e.message.toString()
                        )
                    }
                }
            }

            ProfileEvent.ResetException -> {
                _state.value = state.value.copy(
                    exception = ""
                )
            }

            ProfileEvent.ChangeIsLogOutState -> {
                _state.value = state.value.copy(
                    isLogOut = false
                )
            }

            is ProfileEvent.ChangeSystemTheme -> {

            }
        }
    }
}