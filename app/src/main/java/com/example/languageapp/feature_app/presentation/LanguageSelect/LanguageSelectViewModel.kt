package com.example.languageapp.feature_app.presentation.LanguageSelect

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.languageapp.R
import com.example.languageapp.feature_app.domain.use_case.UserData.GetUserConfigUseCase
import com.example.languageapp.feature_app.domain.use_case.UserData.UpsertUserConfigUseCase
import com.example.languageapp.feature_app.domain.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LanguageSelectViewModel @Inject constructor(
    private val upsertUserConfigUseCase: UpsertUserConfigUseCase,
    private val getUserConfigUseCase: GetUserConfigUseCase
) : ViewModel() {

    private val _state = mutableStateOf(LanguageSelectState())
    val state: State<LanguageSelectState> = _state

    init {
        viewModelScope.launch(Dispatchers.IO){
            getUserConfigUseCase().collect {
                when (it){
                    is NetworkResult.Error<*> -> {}
                    is NetworkResult.Loading<*> -> {}
                    is NetworkResult.Success<*> -> {
                        withContext(Dispatchers.Main){
                            _state.value = state.value.copy(
                                motherLanguage = getStringRes(it.data?.language ?: "en")
                            )
                        }
                    }
                }
            }
        }
    }

    fun onEvent(event: LanguageSelectEvent) {
        when (event) {
            is LanguageSelectEvent.SetLocale -> {
                val language = event.value.platformLocale.language.lowercase()
                _state.value = state.value.copy(
                    motherLanguage = getStringRes(language)
                )
            }

            is LanguageSelectEvent.SelectLanguage -> {
                val language = when (event.stringRes) {
                    R.string.russian -> "ru"
                    R.string.kazakh -> "kk"
                    R.string.chinese -> "chi"
                    R.string.japan -> "jpn"
                    R.string.belarus -> "be"
                    else -> "en"
                }

                viewModelScope.launch(Dispatchers.IO){
                    upsertUserConfigUseCase(systemLanguage = language)
                }

                _state.value = state.value.copy(
                    motherLanguage = event.stringRes
                )
            }
        }
    }

    private fun getStringRes(language: String): Int {
        return when (language) {
            "ru" -> R.string.russian
            "kk" -> R.string.kazakh
            "chi" -> R.string.chinese
            "jpn" -> R.string.japan
            "be" -> R.string.belarus
            else -> R.string.english
        }
    }
}