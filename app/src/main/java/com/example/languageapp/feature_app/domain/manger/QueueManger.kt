package com.example.languageapp.feature_app.domain.manger

interface QueueManger {

    fun getQueue() : Int
    fun setQueue(queue: Int)
}