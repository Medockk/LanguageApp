package com.example.core.domain.usecase.TestCore

import com.example.core.domain.repository.TestCoreRepository

class GetTestCoreByIdUseCase(
    private val testCoreRepository: TestCoreRepository
) {
    operator fun invoke(id: Int) = testCoreRepository.getTestCoreById(id)
}