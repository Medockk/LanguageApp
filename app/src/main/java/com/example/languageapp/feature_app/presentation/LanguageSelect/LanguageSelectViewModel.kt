package com.example.languageapp.feature_app.presentation.LanguageSelect

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.languageapp.R

class LanguageSelectViewModel : ViewModel() {

    private val _state = mutableStateOf(LanguageSelectState())
    val state: State<LanguageSelectState> = _state

    fun onEvent(event: LanguageSelectEvent){
        when (event){
            is LanguageSelectEvent.SetLocale -> {
                val language = event.value.platformLocale.language.lowercase()
                _state.value = state.value.copy(
                    motherLanguage = when (language){
                        "ru" -> R.string.russian
                        "kk" -> R.string.kazakh
                        "chi" -> R.string.chinese
                        "jpn" -> R.string.japan
                        "be" -> R.string.belarus
                        else -> R.string.english
                    }
                )
            }
        }
    }
}