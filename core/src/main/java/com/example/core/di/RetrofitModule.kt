package com.example.core.di

import com.example.core.data.data_source.RetrofitText.RetrofitApiTest
import com.example.core.data.data_source.repository.TestRetrofitRepositoryImpl
import com.example.core.domain.repository.TestRetrofitRepository
import com.example.core.domain.usecase.TestCore.PostTestUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun getRetrofitApi() = Retrofit.Builder()
        .baseUrl("https://petstore.swagger.io/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RetrofitApiTest::class.java)

    @Provides
    @Singleton
    fun getRetrofitRepoTest(retrofitApiTest: RetrofitApiTest): TestRetrofitRepository =
        TestRetrofitRepositoryImpl(retrofitApiTest)
    @Provides
    @Singleton
    fun getPostTestUseCase(retrofitRepository: TestRetrofitRepository)
    = PostTestUseCase(retrofitRepository)
}