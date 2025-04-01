package com.example.languageapp.feature_app.data.repository

import android.util.Log
import com.example.languageapp.feature_app.data.data_source.local.dao.UserConfigDao
import com.example.languageapp.feature_app.data.data_source.local.dao.UserDataDao
import com.example.languageapp.feature_app.data.data_source.remote.Supabase.client
import com.example.languageapp.feature_app.data.model.UserDataConfigImpl
import com.example.languageapp.feature_app.data.model.UserDataModelEntity
import com.example.languageapp.feature_app.domain.model.UserDataConfig
import com.example.languageapp.feature_app.domain.model.UserDataModel
import com.example.languageapp.feature_app.domain.repository.UserDataRepository
import com.example.languageapp.feature_app.domain.utils.NetworkResult
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration

class UserDataRepositoryImpl(
    private val userDataDao: UserDataDao,
    private val userConfigDao: UserConfigDao
) : UserDataRepository {

    override suspend fun clearUserDataAndConfig() {
        userConfigDao.clearUserConfig()
        userDataDao.clearData()
    }

    override suspend fun upsertUserConfig(
        isSystemInDarkTheme: Boolean?,
        systemLanguage: String?,
    ) {
        Log.e("s", userConfigDao.getUserConfig().toString())
        userConfigDao.upsertConfig(
            UserDataConfigImpl(
                isSystemInDarkTheme = isSystemInDarkTheme
                    ?: (userConfigDao.getUserConfig()?.isSystemInDarkTheme ?: false),
                language = systemLanguage ?: (userConfigDao.getUserConfig()?.language ?: "en")
            )
        )
        Log.e("s", isSystemInDarkTheme.toString())
        Log.e("s", userConfigDao.getUserConfig().toString())
    }

    override suspend fun getUserConfig() = flow<NetworkResult<UserDataConfig>> {

        if (userConfigDao.getUserConfig() == null) {
            userConfigDao.upsertConfig(
                UserDataConfigImpl(
                    0, "", isSystemInDarkTheme = false
                )
            )
        }
        emit(NetworkResult.Success(userConfigDao.getUserConfig()))
    }

    override suspend fun getUserData(): Flow<NetworkResult<UserDataModel>> {

        val userID = getUserId()
        return flow<NetworkResult<UserDataModel>> {

            emit(NetworkResult.Loading())
            emit(NetworkResult.Success(userDataDao.getUserData(userID)))

            Log.e("ex", "before supa")
            var data: UserDataModelEntity? = null
            try {
                data = client.postgrest["Users"].select {
                    filter {
                        eq("userID", userID)
                    }
                }.decodeSingle<UserDataModelEntity>()
            } catch (e: Exception) {
                Log.e("ex", "supa exeption ${e.message.toString()}")
            }
            Log.e("ex", "after supa")

            emit(NetworkResult.Success(data))
            if (data != null) {
                userDataDao.upsertData(data)
            }
        }.catch {
            Log.e("ex", it.message.toString())
            emit(NetworkResult.Error(it.localizedMessage))
        }
    }


    override suspend fun updateAvatar(byteArray: ByteArray) {

        val userId = getUserId()

        val bucket = client.storage.from("avatars")
        bucket.update(
            userId,
            byteArray
        ) {
            upsert = true
        }
        val url = bucket.createSignedUrl(userId, Duration.INFINITE)
        userDataDao.upsertData(userDataDao.getUserData(userId).copy(avatar = url))
        client.postgrest["Users"].update(
            mapOf(
                "avatar" to url
            )
        ) {
            filter { eq("userID", userId) }
        }
    }

    private suspend fun getUserId(): String {
        client.auth.awaitInitialization()
        return client.auth.currentUserOrNull()?.id ?: ""
    }
}