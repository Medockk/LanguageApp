package com.example.core.domain.repository

import com.example.core.domain.model.TestCoreModel

interface TestCoreRepository {

    fun getTestCoreById(id: Int) : TestCoreModel
    fun addTestCore(id: Int)
}