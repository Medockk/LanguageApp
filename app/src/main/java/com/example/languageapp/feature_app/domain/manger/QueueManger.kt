package com.example.languageapp.feature_app.domain.manger

interface QueueManger {

    suspend fun getQueue() : Int
    suspend fun setQueue(queue: Int)
}