package com.example.languageapp.feature_app.data.repository

import com.example.languageapp.feature_app.data.data_source.local.dao.UserDataDao
import com.example.languageapp.feature_app.data.data_source.remote.Supabase.client
import com.example.languageapp.feature_app.data.model.UserDataModelEntity
import com.example.languageapp.feature_app.domain.model.UserDataModel
import com.example.languageapp.feature_app.domain.repository.UserDataRepository
import com.example.languageapp.feature_app.domain.utils.NetworkResult
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration

class UserDataRepositoryImpl(
    private val userDataDao: UserDataDao
) : UserDataRepository {

    override suspend fun getUserData() = flow<NetworkResult<UserDataModel>> {

        emit(NetworkResult.Loading())

        val userID = getUserId()
        emit(NetworkResult.Success(userDataDao.getUserData(userID)))

        val data = client.postgrest["Users"].select {
            filter {
                eq("userID", userID)
            }
        }.decodeList<UserDataModelEntity>()

        emit(NetworkResult.Success(data[0]))
        userDataDao.upsertData(data[0])
    }.catch {
        emit(NetworkResult.Error(it.localizedMessage))
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