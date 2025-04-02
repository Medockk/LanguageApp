package com.example.languageapp.feature_app.domain.use_case.UserData

import com.example.languageapp.feature_app.domain.repository.UserDataRepository

class UpsertUserScoreUseCase(
    private val userDataRepository: UserDataRepository
) {

    suspend operator fun invoke(score: String){
        userDataRepository.upsertUserScore(score)
    }
}