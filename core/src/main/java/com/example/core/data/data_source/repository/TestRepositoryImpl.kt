package com.example.core.data.data_source.repository

import com.example.core.data.dto.TestCoreModelImpl
import com.example.core.domain.model.TestCoreModel
import com.example.core.domain.repository.TestCoreRepository

class TestRepositoryImpl : TestCoreRepository {

    override fun getTestCoreById(id: Int): TestCoreModel {
        return TestCoreModelImpl(id)
    }

    override fun addTestCore(id: Int) {
        TestCoreModelImpl(id)
    }
}