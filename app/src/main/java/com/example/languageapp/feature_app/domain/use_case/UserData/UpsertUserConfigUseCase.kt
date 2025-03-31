package com.example.languageapp.feature_app.domain.use_case.UserData

import com.example.languageapp.feature_app.domain.repository.UserDataRepository

class UpsertUserConfigUseCase(
    private val userDataRepository: UserDataRepository
) {

    suspend operator fun invoke(systemTheme: Boolean){
        userDataRepository.upsertUserConfig(systemTheme)
    }
}