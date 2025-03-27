package com.example.languageapp.feature_app.presentation.LanguageSelect

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LanguageSelectViewModel : ViewModel() {

    private val _state = mutableStateOf(LanguageSelectState())
    val state: State<LanguageSelectState> = _state

    fun onEvent(event: LanguageSelectEvent){
        when (event){
            is LanguageSelectEvent.SetLocale -> {
                val language = event.value.language
                _state.value = state.value.copy(
                    motherLanguage = when (language){
                        "ru" -> "Russian"
                        "kk" -> "Kazakh"
                        "chi" -> "Chinese"
                        "jpn" -> "Japan"
                        "be" -> "Belarus"
                        else -> "English"
                    }
                )
            }
        }
    }
}