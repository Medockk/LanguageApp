package com.example.languageapp.feature_app.domain.repository

interface AuthRepository {

    suspend fun signIn(mail: String, password: String)
    suspend fun signUp(firstName: String, lastName: String, mail: String, password: String)
    suspend fun signOut()
}