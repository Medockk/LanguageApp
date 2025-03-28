package com.example.languageapp.feature_app.domain.use_case.Auth

import com.example.languageapp.feature_app.domain.repository.AuthRepository

class SignUpUseCase(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(firstName: String, lastName: String, mail: String, password: String){
        authRepository.signUp(firstName, lastName, mail, password)
    }
}