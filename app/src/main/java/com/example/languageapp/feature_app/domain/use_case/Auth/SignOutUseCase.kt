package com.example.languageapp.feature_app.domain.use_case.Auth

import com.example.languageapp.feature_app.domain.repository.AuthRepository

class SignOutUseCase(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(){
        authRepository.signOut()
    }
}