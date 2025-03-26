package com.example.languageapp.feature_app.presentation.OnBoard

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.languageapp.feature_app.domain.use_case.Queue.GetQueueUseCase
import com.example.languageapp.feature_app.domain.use_case.Queue.SetQueueUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class OnBoardViewModel @Inject constructor(
    private val getQueueUseCase: GetQueueUseCase,
    private val setQueueUseCase: SetQueueUseCase
) : ViewModel() {

    private val _state = mutableStateOf(OnBoardState())
    val state: State<OnBoardState> = _state

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val queue = getQueueUseCase()
            withContext(Dispatchers.Main){
                _state.value = state.value.copy(
                    currentPage = if (queue != -1) queue else 0,
                    isComplete = queue == -1
                )
            }
        }
    }

    fun onEvent(event: OnBoardEvent){
        when (event){
            is OnBoardEvent.NextPage -> {
                if (_state.value.list.size > event.page){
                    viewModelScope.launch(Dispatchers.IO) {
                        try {
                            setQueueUseCase(event.page)
                            _state.value = state.value.copy(
                                currentPage = event.page
                            )
                        } catch (e: Exception) {
                            _state.value = state.value.copy(
                                exception = e.message.toString()
                            )
                        }
                    }
                }else{
                    viewModelScope.launch(Dispatchers.IO) {
                        try {
                            setQueueUseCase(-1)
                            _state.value = state.value.copy(
                                isComplete = true
                            )
                        } catch (e: Exception) {
                            _state.value = state.value.copy(exception = e.message.toString())
                        }
                    }
                }
            }

            OnBoardEvent.ResetException -> {
                _state.value = state.value.copy(exception = "")
            }

            OnBoardEvent.SkipOnBoardClick -> {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        setQueueUseCase(-1)
                        _state.value = state.value.copy(isComplete = true)
                    } catch (e: Exception) {
                        _state.value = state.value.copy(
                            exception = e.message.toString()
                        )
                    }
                }
            }
        }
    }
}