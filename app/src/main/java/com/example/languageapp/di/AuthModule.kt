package com.example.languageapp.di

import com.example.languageapp.feature_app.data.data_source.local.dao.UserDataDao
import com.example.languageapp.feature_app.data.repository.AuthRepositoryImpl
import com.example.languageapp.feature_app.domain.repository.AuthRepository
import com.example.languageapp.feature_app.domain.use_case.Auth.SignInUseCase
import com.example.languageapp.feature_app.domain.use_case.Auth.SignOutUseCase
import com.example.languageapp.feature_app.domain.use_case.Auth.SignUpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun getRepo(userDataDao: UserDataDao): AuthRepository {
        return AuthRepositoryImpl(userDataDao)
    }

    @Provides
    @Singleton
    fun signIn(authRepository: AuthRepository): SignInUseCase {
        return SignInUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun signUp(authRepository: AuthRepository): SignUpUseCase {
        return SignUpUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun signOut(authRepository: AuthRepository): SignOutUseCase {
        return SignOutUseCase(authRepository)
    }
}