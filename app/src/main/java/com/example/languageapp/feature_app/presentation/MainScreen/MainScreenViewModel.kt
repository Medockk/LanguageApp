package com.example.languageapp.feature_app.presentation.MainScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.languageapp.feature_app.domain.use_case.UserData.GetUserDataUseCase
import com.example.languageapp.feature_app.domain.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val getUserDataUseCase: GetUserDataUseCase
) : ViewModel() {

    private val _state = mutableStateOf(MainScreenState())
    val state: State<MainScreenState> = _state

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getUserData()
            } catch (e: Exception) {
                _state.value = state.value.copy(
                    exception = e.message.toString()
                )
            }
        }
    }

    private suspend fun getUserData() {
        getUserDataUseCase().collect{
            when (it){
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
                    withContext(Dispatchers.Main){
                        _state.value = state.value.copy(
                            userImage = it.data?.avatar ?: "",
                            userName = (it.data?.firstName ?: "") + " " + (it.data?.lastName ?: ""),
                            showIndicator = false
                        )
                    }
                }
            }
        }
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