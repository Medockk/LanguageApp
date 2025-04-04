package com.example.languageapp.feature_app.presentation.WordPractice

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WordPracticeViewModel @Inject constructor() : ViewModel() {

    private val _state = mutableStateOf(WordPracticeState())
    val state: State<WordPracticeState> = _state

    fun onEvent(event: WordPracticeEvent){
        when (event){
            WordPracticeEvent.CheckClick -> {
                _state.value = state.value.copy(
                    isUserCheckingAnswer = true
                )
            }
            is WordPracticeEvent.SetAnswer -> {
                _state.value = state.value.copy(
                    userAnswer = event.value
                )
            }
        }
    }
}