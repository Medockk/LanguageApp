package com.example.languageapp.feature_app.domain.repository

import com.example.languageapp.feature_app.domain.model.UserDataConfig
import com.example.languageapp.feature_app.domain.model.UserDataModel
import com.example.languageapp.feature_app.domain.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {

    suspend fun getUserData() : Flow<NetworkResult<UserDataModel>>
    suspend fun updateAvatar(byteArray: ByteArray)

    suspend fun getUserConfig() : Flow<NetworkResult<UserDataConfig>>
    suspend fun upsertUserConfig(isSystemInDarkTheme: Boolean?, systemLanguage: String?)
    suspend fun clearUserDataAndConfig()
}