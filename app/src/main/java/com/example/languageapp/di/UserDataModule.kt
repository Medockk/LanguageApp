package com.example.languageapp.di

import android.content.Context
import com.example.languageapp.feature_app.data.data_source.local.dao.UserDataDao
import com.example.languageapp.feature_app.data.data_source.local.database.UserDataDatabase
import com.example.languageapp.feature_app.data.repository.UserDataRepositoryImpl
import com.example.languageapp.feature_app.domain.repository.UserDataRepository
import com.example.languageapp.feature_app.domain.use_case.UserData.GetUserDataUseCase
import com.example.languageapp.feature_app.domain.use_case.UserData.UpdateAvatarUseCase
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
    fun getDao(@ApplicationContext context: Context) : UserDataDao{
        val database = UserDataDatabase.createDatabase(context)
        return database.userDao
    }
    @Provides
    @Singleton
    fun getRepo(userDataDao: UserDataDao) : UserDataRepository{
        return UserDataRepositoryImpl(userDataDao)
    }

    @Provides
    @Singleton
    fun getUserData(userDataRepository: UserDataRepository) : GetUserDataUseCase{
        return GetUserDataUseCase(userDataRepository)
    }
    @Provides
    @Singleton
    fun getUpdateAvatar(userDataRepository: UserDataRepository) : UpdateAvatarUseCase{
        return UpdateAvatarUseCase(userDataRepository)
    }
}