package com.example.languageapp.feature_app.domain.use_case.Auth

import com.example.languageapp.feature_app.domain.repository.AuthRepository

class SignInUseCase(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(mail: String, password: String){
        authRepository.signIn(mail, password)
    }
}