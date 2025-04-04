package com.example.languageapp.feature_app.domain.use_case.UserData

import com.example.languageapp.feature_app.domain.model.UserDataModel
import com.example.languageapp.feature_app.domain.repository.UserDataRepository
import com.example.languageapp.feature_app.domain.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

class GetUserScoreUseCase(
    private val userDataRepository: UserDataRepository
) {

    suspend operator fun invoke() : Flow<NetworkResult<UserDataModel>>{
        return userDataRepository.getUserScore()
    }
}