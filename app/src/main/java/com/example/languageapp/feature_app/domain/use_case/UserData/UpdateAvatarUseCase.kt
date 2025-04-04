package com.example.languageapp.feature_app.domain.use_case.UserData

import com.example.languageapp.feature_app.domain.repository.UserDataRepository

class UpdateAvatarUseCase(
    private val userDataRepository: UserDataRepository
) {

    suspend operator fun invoke(byteArray: ByteArray){
        userDataRepository.updateAvatar(byteArray)
    }
}