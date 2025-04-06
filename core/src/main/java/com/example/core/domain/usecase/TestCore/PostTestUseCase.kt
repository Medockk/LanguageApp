package com.example.core.domain.usecase.TestCore

import com.example.core.domain.repository.TestRetrofitRepository

class PostTestUseCase(
    private val retrofitRepository: TestRetrofitRepository
) {

    suspend operator fun invoke() = retrofitRepository.postTest()
}