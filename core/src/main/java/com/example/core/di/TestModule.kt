package com.example.core.di

import com.example.core.data.data_source.repository.TestRepositoryImpl
import com.example.core.domain.repository.TestCoreRepository
import com.example.core.domain.usecase.TestCore.AddTestCoreUseCase
import com.example.core.domain.usecase.TestCore.GetTestCoreByIdUseCase
import com.example.core.domain.usecase.TestCore.IsStrongPasswordUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestModule {

    @Provides
    @Singleton
    fun getRepository() : TestCoreRepository = TestRepositoryImpl()

    @Provides
    @Singleton
    fun addTestCoreUseCase(testCoreRepository: TestCoreRepository) =
        AddTestCoreUseCase(testCoreRepository)

    @Singleton
    @Provides
    fun getTestCoreById(testCoreRepository: TestCoreRepository) =
        GetTestCoreByIdUseCase(testCoreRepository)
    @Singleton
    @Provides
    fun isStrongPasswordUseCase() =
        IsStrongPasswordUseCase()
}