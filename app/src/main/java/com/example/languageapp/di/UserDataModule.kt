package com.example.languageapp.di

import android.content.Context
import com.example.languageapp.feature_app.data.data_source.local.dao.UserConfigDao
import com.example.languageapp.feature_app.data.data_source.local.dao.UserDataDao
import com.example.languageapp.feature_app.data.data_source.local.database.UserDataConfigDatabase
import com.example.languageapp.feature_app.data.data_source.local.database.UserDataDatabase
import com.example.languageapp.feature_app.data.repository.UserDataRepositoryImpl
import com.example.languageapp.feature_app.domain.repository.UserDataRepository
import com.example.languageapp.feature_app.domain.use_case.UserData.ClearUserDataAndConfigUseCase
import com.example.languageapp.feature_app.domain.use_case.UserData.GetTopUsersUseCase
import com.example.languageapp.feature_app.domain.use_case.UserData.GetUserConfigUseCase
import com.example.languageapp.feature_app.domain.use_case.UserData.GetUserDataUseCase
import com.example.languageapp.feature_app.domain.use_case.UserData.GetUserScoreUseCase
import com.example.languageapp.feature_app.domain.use_case.UserData.UpdateAvatarUseCase
import com.example.languageapp.feature_app.domain.use_case.UserData.UpsertUserConfigUseCase
import com.example.languageapp.feature_app.domain.use_case.UserData.UpsertUserScoreUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserDataModule {

    @Provides
    @Singleton
    fun getDao(@ApplicationContext context: Context): UserDataDao {
        val database = UserDataDatabase.createDatabase(context)
        return database.userDao
    }

    @Provides
    @Singleton
    fun getConfig(@ApplicationContext context: Context): UserConfigDao {
        val database = UserDataConfigDatabase.createDatabase(context)
        return database.userConfig
    }

    @Provides
    @Singleton
    fun getRepo(
        userDataDao: UserDataDao,
        userConfigDao: UserConfigDao
    ): UserDataRepository {
        return UserDataRepositoryImpl(userDataDao, userConfigDao)
    }

    @Provides
    @Singleton
    fun getUserData(userDataRepository: UserDataRepository): GetUserDataUseCase {
        return GetUserDataUseCase(userDataRepository)
    }

    @Provides
    @Singleton
    fun getUpdateAvatar(userDataRepository: UserDataRepository): UpdateAvatarUseCase {
        return UpdateAvatarUseCase(userDataRepository)
    }

    @Provides
    @Singleton
    fun getUserConfig(userDataRepository: UserDataRepository): GetUserConfigUseCase {
        return GetUserConfigUseCase(userDataRepository)
    }

    @Provides
    @Singleton
    fun updateUserConfig(userDataRepository: UserDataRepository): UpsertUserConfigUseCase {
        return UpsertUserConfigUseCase(userDataRepository)
    }

    @Provides
    @Singleton
    fun clearDataAndConfig(userDataRepository: UserDataRepository): ClearUserDataAndConfigUseCase {
        return ClearUserDataAndConfigUseCase(userDataRepository)
    }
    @Provides
    @Singleton
    fun upsertUserScore(userDataRepository: UserDataRepository): UpsertUserScoreUseCase {
        return UpsertUserScoreUseCase(userDataRepository)
    }
    @Provides
    @Singleton
    fun getUserScore(userDataRepository: UserDataRepository) = GetUserScoreUseCase(userDataRepository)
    @Provides
    @Singleton
    fun getTopUsers(userDataRepository: UserDataRepository) = GetTopUsersUseCase(userDataRepository)
}