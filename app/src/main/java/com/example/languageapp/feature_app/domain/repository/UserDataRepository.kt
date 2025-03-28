package com.example.languageapp.feature_app.domain.repository

import com.example.languageapp.feature_app.domain.model.UserDataModel
import com.example.languageapp.feature_app.domain.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {

    suspend fun getUserData() : Flow<NetworkResult<UserDataModel>>
    suspend fun updateAvatar(byteArray: ByteArray)
}