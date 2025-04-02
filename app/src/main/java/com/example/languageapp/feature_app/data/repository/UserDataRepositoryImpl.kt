package com.example.languageapp.feature_app.data.repository

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

    override suspend fun upsertUserScore(score: String) {
        userDataDao.upsertData(userDataDao.getUserData()!!.copy(score = score))

        val userID = userDataDao.getUserData()?.userID ?: getUserId()
        client.postgrest["Users"].update(
            mapOf(
                "score" to score
            )
        ) {
            filter { eq("userID", userID) }
        }
    }

    override suspend fun getTopUsers(): Flow<NetworkResult<List<UserDataModel>>> {

        val userID = userDataDao.getUserData()?.userID ?: getUserId()

        return flow<NetworkResult<List<UserDataModel>>> {
            emit(NetworkResult.Loading())

            val remoteUserPositionDate = client.postgrest["Users"].select {
                filter { eq("userID", userID) }
            }.decodeSingle<UserDataModelEntity>()
            val topUsers = client.postgrest["Users"].select {
                limit(3)
                filter {
                    gt("score", remoteUserPositionDate.score)
                    gte("score", remoteUserPositionDate.score)
                }
            }.decodeList<UserDataModelEntity>()

            val topList = ArrayList<UserDataModel>()
            for (i in 0 until if (topUsers.size > 3) 3 else topUsers.size) {
                topList.add(topUsers[i])
            }
            topList.add(remoteUserPositionDate)


            emit(NetworkResult.Success(topList))
            userDataDao.upsertData(remoteUserPositionDate)

        }.catch {
            emit(NetworkResult.Error(it.localizedMessage))
        }
    }

    override suspend fun getUserScore(): Flow<NetworkResult<UserDataModel>> {

        val userID = userDataDao.getUserData()?.userID ?: getUserId()

        return flow<NetworkResult<UserDataModel>> {
            emit(NetworkResult.Loading())
            emit(NetworkResult.Success(userDataDao.getUserData()))

            val remoteData = client.postgrest["Users"].select {
                filter { eq("userID", userID) }
            }.decodeSingle<UserDataModelEntity>()
            emit(NetworkResult.Success(remoteData))

            userDataDao.upsertData(remoteData)
        }.catch {
            emit(NetworkResult.Error(it.localizedMessage))
        }
    }

    override suspend fun clearUserDataAndConfig() {
        userConfigDao.clearUserConfig()
        userDataDao.clearData()
    }

    override suspend fun upsertUserConfig(
        isSystemInDarkTheme: Boolean?,
        systemLanguage: String?,
    ) {
        userConfigDao.upsertConfig(
            UserDataConfigImpl(
                isSystemInDarkTheme = isSystemInDarkTheme
                    ?: (userConfigDao.getUserConfig()?.isSystemInDarkTheme ?: false),
                language = systemLanguage ?: (userConfigDao.getUserConfig()?.language ?: "en")
            )
        )
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

        val userID = userDataDao.getUserData()?.userID ?: getUserId()
        return flow<NetworkResult<UserDataModel>> {

            emit(NetworkResult.Loading())
            emit(NetworkResult.Success(userDataDao.getUserData(userID)))

            var data: UserDataModelEntity? = null
            try {
                data = client.postgrest["Users"].select {
                    filter {
                        eq("userID", userID)
                    }
                }.decodeSingle<UserDataModelEntity>()
            } catch (_: Exception) {

            }

            emit(NetworkResult.Success(data))
            if (data != null) {
                userDataDao.upsertData(data)
            }
        }.catch {
            emit(NetworkResult.Error(it.localizedMessage))
        }
    }


    override suspend fun updateAvatar(byteArray: ByteArray) {

        val userId = userDataDao.getUserData()?.userID ?: getUserId()

        val bucket = client.storage.from("avatars")
        bucket.update(
            userId,
            byteArray
        ) {
            upsert = true
        }
        val url = bucket.createSignedUrl(userId, Duration.INFINITE)
        userDataDao.upsertData(userDataDao.getUserData(userId)!!.copy(avatar = url))
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