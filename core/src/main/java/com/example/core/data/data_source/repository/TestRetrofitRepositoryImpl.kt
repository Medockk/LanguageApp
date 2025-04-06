package com.example.core.data.data_source.repository

import com.example.core.data.data_source.RetrofitText.PostRegistrationResponse
import com.example.core.data.data_source.RetrofitText.RetrofitApiTest
import com.example.core.domain.repository.TestRetrofitRepository

class TestRetrofitRepositoryImpl(
    private val retrofitApiTest: RetrofitApiTest
) : TestRetrofitRepository {

    override suspend fun postTest() {
        retrofitApiTest.postRegistration(PostRegistrationResponse())
    }
}