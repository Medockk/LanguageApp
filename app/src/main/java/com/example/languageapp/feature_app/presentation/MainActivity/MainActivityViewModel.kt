package com.example.languageapp.feature_app.presentation.MainActivity

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.languageapp.feature_app.domain.use_case.UserData.GetUserConfigUseCase
import com.example.languageapp.feature_app.domain.use_case.UserData.GetUserDataUseCase
import com.example.languageapp.feature_app.domain.use_case.UserData.UpsertUserConfigUseCase
import com.example.languageapp.feature_app.domain.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getUserDataUseCase: GetUserDataUseCase,
    private val getUserConfigUseCase: GetUserConfigUseCase,
    private val upsertUserConfigUseCase: UpsertUserConfigUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _state = mutableStateOf(MainActivityState())
    val state: State<MainActivityState> = _state

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getUserData()
            getUserConfig()
        }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                checkConnection()
            } catch (_: Exception) {

            }
        }
    }

    private suspend fun checkConnection() {
        while (true) {
            val network =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = network.activeNetworkInfo

            withContext(Dispatchers.Main) {
                _state.value = state.value.copy(
                    haveConnection = info?.isConnected ?: false
                )
            }

            delay(5000)
        }
    }

    private suspend fun getUserData() {
        getUserDataUseCase().collect {
            when (it) {
                is NetworkResult.Error<*> -> {}
                is NetworkResult.Loading<*> -> {}
                is NetworkResult.Success<*> -> {
                    withContext(Dispatchers.Main) {
                        _state.value = state.value.copy(
                            isUserDataNotEmpty = true
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: MainActivityEvent) {
        when (event) {
            is MainActivityEvent.ChangeSystemLanguage -> {
                _state.value = state.value.copy(
                    systemLanguage = event.value
                )
            }

            is MainActivityEvent.ChangeSystemTheme -> {
                _state.value = state.value.copy(
                    isSystemInDarkTheme = event.value
                )

                viewModelScope.launch(Dispatchers.IO) {
                    upsertUserConfigUseCase(event.value)
                }
            }

            MainActivityEvent.GetUserConfig -> {
                viewModelScope.launch(Dispatchers.IO) {
                    getUserConfig()
                }
            }
        }
    }

    private suspend fun getUserConfig() {
        try {
            getUserConfigUseCase().collect { config ->
                when (config) {
                    is NetworkResult.Error<*> -> {}
                    is NetworkResult.Loading<*> -> {}
                    is NetworkResult.Success<*> -> {
                        withContext(Dispatchers.Main) {
                            _state.value = state.value.copy(
                                isSystemInDarkTheme = config.data?.isSystemInDarkTheme ?: false,
                                systemLanguage = config.data?.language ?: "en"
                            )
                        }
                    }
                }
            }
        } catch (_: Exception) {
        }
    }
}